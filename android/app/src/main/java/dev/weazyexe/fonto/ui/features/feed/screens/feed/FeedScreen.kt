package dev.weazyexe.fonto.ui.features.feed.screens.feed

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.core.ui.utils.ReceiveNewEffect
import dev.weazyexe.fonto.features.feed.FeedEffect
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.browser.InAppBrowser
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalCategoryPickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalDateRangePickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalFeedPickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalNavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.CategoryPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.DateRangePickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.FeedPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ManageFeedResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import kotlinx.coroutines.flow.Flow

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    rootPaddingValues: PaddingValues,
    viewModel: FeedViewModel,
    navigateTo: NavigateTo,
    manageFeedResultRecipientProvider: ManageFeedResults,
    dateRangePickerResultRecipientProvider: DateRangePickerResults,
    feedPickerResults: FeedPickerResults,
    categoryPickerResults: CategoryPickerResults,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState(FeedViewState())
    val snackbarHostState = remember { SnackbarHostState() }

    HandleEffects(viewModel.effects)

    /*manageFeedResultRecipientProvider.invoke().handleResults { result ->
        if (result) {
            viewModel.loadNewsline()
        }
    }*/

    CompositionLocalProvider(
        LocalNavigateTo provides navigateTo,
        LocalDateRangePickerResults provides dateRangePickerResultRecipientProvider,
        LocalFeedPickerResults provides feedPickerResults,
        LocalCategoryPickerResults provides categoryPickerResults
    ) {
        FeedBody(
            posts = state.posts,
            scrollState = state.scrollState,
            rootPaddingValues = rootPaddingValues,
            snackbarHostState = snackbarHostState,
            paginationState = state.paginationState,
            isSwipeRefreshing = state.isSwipeRefreshing,
            isSearchBarActive = state.isSearchBarActive,
            onPostClick = { viewModel.openPost(it) },
            onPostSaveClick = { /*viewModel::savePost*/ },
            onScroll = { /*viewModel::onScroll*/ },
            onManageFeedClick = { navigateTo(ManageFeedScreenDestination()) },
            onRefreshClick = { /*viewModel::loadNewsline*/ },
            fetchNextBatch = { viewModel.loadMorePosts() },
            onSearchBarActiveChange = { /*viewModel::onSearchBarActiveChange*/ }
        )
    }
}

@Composable
private fun HandleEffects(
    effects: Flow<FeedEffect>
) {
    val context = LocalContext.current
    ReceiveNewEffect(effects) {
        when (this) {
            is FeedEffect.OpenPostInApp -> {
                InAppBrowser.openPost(context, link, theme)
            }

            is FeedEffect.OpenPostInBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }
        }
    }
}
