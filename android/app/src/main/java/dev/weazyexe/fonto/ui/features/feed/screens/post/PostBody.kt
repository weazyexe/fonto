package dev.weazyexe.fonto.ui.features.feed.screens.post

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.LoadingPane
import dev.weazyexe.fonto.core.ui.components.error.ErrorPane
import dev.weazyexe.fonto.core.ui.components.error.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.utils.withAlgorithmicDarkening


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBody(
    postLoadState: LoadState<Post>,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.post_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LoadStateComponent(
                loadState = postLoadState,
                onLoading = { LoadingPane() },
                onError = { ErrorPane(params = it.error.asErrorPaneParams()) },
                onSuccess = { PostView(post = it) }
            )
        }
    }
}

@Composable
private fun PostView(post: Post) {
    val state = rememberWebViewState(post.link)

    Box {
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        WebView(
            state = state,
            onCreated = {
                it.withAlgorithmicDarkening()
                it.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                it.settings.javaScriptEnabled = true
            },
            client = object : AccompanistWebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    view.context.startActivity(intent)
                    return true
                }
            }
        )
    }
}
