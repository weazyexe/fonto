package dev.weazyexe.fonto.ui.features.feed.screens.feed

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.browser.InAppBrowser
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    rootPaddingValues: PaddingValues,
    viewModel: FeedViewModel,
    navigateTo: NavigateTo,
    manageFeedResultRecipientProvider: () -> ResultRecipient<ManageFeedScreenDestination, Boolean>
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    manageFeedResultRecipientProvider().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.loadNewsline()
                }
            }
        }
    }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is FeedEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(context.getString(message, *args))
            }
            is FeedEffect.OpenPostInApp -> {
                InAppBrowser.openPost(context, link, theme)
            }
            is FeedEffect.OpenPostInBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }
        }
    }

    FeedBody(
        newslineLoadState = state.newslineLoadState,
        filters = state.filters,
        scrollState = state.scrollState,
        rootPaddingValues = rootPaddingValues,
        snackbarHostState = snackbarHostState,
        paginationState = state.newslinePaginationState,
        isSwipeRefreshing = state.isSwipeRefreshing,
        onPostClick = viewModel::openPost,
        onScroll = viewModel::onScroll,
        onManageFeedClick = { navigateTo(ManageFeedScreenDestination()) },
        onSearchClick = {},
        onRefreshClick = viewModel::loadNewsline,
        fetchNextBatch = viewModel::getNextPostsBatch,
        onFilterChange = viewModel::applyFilters
    )
}
