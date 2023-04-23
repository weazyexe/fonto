package dev.weazyexe.fonto.ui.features.feed.screens.post

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import org.koin.androidx.compose.koinViewModel

@Destination(navArgsDelegate = PostScreenArgs::class)
@Composable
fun PostScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<PostViewModel>()
    val state by viewModel.uiState.collectAsState()
    
    ReceiveEffect(viewModel.effects) {
        when (this) {
            is PostEffect.OpenShareDialog -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                val shareDialog = Intent.createChooser(intent, null)
                context.startActivity(shareDialog)
            }
        }
    }

    PostBody(
        postLoadState = state.post,
        onBackClick = { navController.navigateUp() },
        onShareClick = viewModel::openShareDialog
    )
}
