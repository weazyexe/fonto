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
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
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
import dev.weazyexe.fonto.util.handleResults
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
    val state by viewModel.state.collectAsState(FeedViewState())
    val snackbarHostState = remember { SnackbarHostState() }

    HandleEffects(viewModel.effects, snackbarHostState)

    manageFeedResultRecipientProvider.invoke().handleResults { result ->
        if (result) {
            viewModel.loadPosts(isSwipeRefreshing = false)
        }
    }

    CompositionLocalProvider(
        LocalNavigateTo provides navigateTo,
        LocalDateRangePickerResults provides dateRangePickerResultRecipientProvider,
        LocalFeedPickerResults provides feedPickerResults,
        LocalCategoryPickerResults provides categoryPickerResults
    ) {
        FeedBody(
            posts = state.posts,
            rootPaddingValues = rootPaddingValues,
            snackbarHostState = snackbarHostState,
            paginationState = state.paginationState,
            isSwipeRefreshing = state.isSwipeRefreshing,
            isSearchBarActive = state.isSearchBarActive,
            onPostClick = { viewModel.openPost(it) },
            onPostSaveClick = { viewModel.savePost(it) },
            onManageFeedClick = { navigateTo(ManageFeedScreenDestination()) },
            onRefreshClick = { viewModel.loadPosts(it) },
            loadMorePosts = { viewModel.loadMorePosts() },
            onSearchBarActiveChange = { viewModel.onSearchBarActiveChange(it) }
        )
    }
}

@Composable
private fun HandleEffects(
    effects: Flow<FeedEffect>,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    ReceiveEffect(effects) {
        when (this) {
            is FeedEffect.OpenPostInApp -> {
                InAppBrowser.openPost(context, link, theme)
            }

            is FeedEffect.OpenPostInBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }

            is FeedEffect.ShowPostSavingErrorMessage -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(
                        if (isSaving) {
                            StringResources.feed_post_saving_error
                        } else {
                            StringResources.feed_post_removing_from_bookmarks_error
                        }
                    )
                )
            }

            is FeedEffect.ShowPostSavedMessage -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(
                        if (isSaving) {
                            StringResources.feed_post_saved_to_bookmarks
                        } else {
                            StringResources.feed_post_removed_from_bookmarks
                        }
                    )
                )
            }
        }
    }
}
