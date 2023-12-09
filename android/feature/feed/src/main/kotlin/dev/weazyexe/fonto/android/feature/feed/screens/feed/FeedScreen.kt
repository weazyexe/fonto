package dev.weazyexe.fonto.android.feature.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.android.feature.feed.screens.feed.composition.LocalNavController
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnLoadMore
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnLoadPostMeta
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnManageFeedClick
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnPostClick
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnPostSaveClick
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnRefresh
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.View.OnSearchBarActiveChange

@Destination
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        FeedBody(
            posts = state.posts,
            lazyListState = lazyListState,
            rootPaddingValues = rootPaddingValues,
            snackbarHostState = snackbarHostState,
            paginationState = state.paginationState,
            isSwipeRefreshing = state.isSwipeRefreshing,
            isSearchBarActive = state.isSearchBarActive,
            initialFirstVisibleItemIndex = 0, // TODO delete
            initialFirstVisibleItemOffset = 0, // TODO delete
            onPostClick = { viewModel.dispatch(OnPostClick(it)) },
            onPostSaveClick = { viewModel.dispatch(OnPostSaveClick(it)) },
            onPostLoadImage = { viewModel.dispatch(OnLoadPostMeta(it)) },
            onManageFeedClick = { viewModel.dispatch(OnManageFeedClick) },
            onRefreshClick = { viewModel.dispatch(OnRefresh) },
            loadMorePosts = { viewModel.dispatch(OnLoadMore) },
            onSearchBarActiveChange = { viewModel.dispatch(OnSearchBarActiveChange(it)) },
            onScroll = { index, offset ->  }
        )
    }
}
