package dev.weazyexe.fonto.android.feature.feed.screens.feed

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import dev.weazyexe.fonto.android.feature.feed.screens.categorypicker.CategoryPickerResult
import dev.weazyexe.fonto.android.feature.feed.screens.daterangepicker.DateRangePickerResult
import dev.weazyexe.fonto.android.feature.feed.screens.feed.browser.InAppBrowser
import dev.weazyexe.fonto.android.feature.feed.screens.feed.composition.LocalCategoryPickerResult
import dev.weazyexe.fonto.android.feature.feed.screens.feed.composition.LocalDateRangePickerResult
import dev.weazyexe.fonto.android.feature.feed.screens.feed.composition.LocalFeedPickerResult
import dev.weazyexe.fonto.android.feature.feed.screens.feed.composition.LocalNavController
import dev.weazyexe.fonto.android.feature.feed.screens.feedpicker.FeedPickerResult
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.feed.FeedEffect
import kotlinx.coroutines.flow.Flow

@RootNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    rootPaddingValues: PaddingValues,
    navController: NavController,
    dateRangePickerResult: DateRangePickerResult,
    feedPickerResult: FeedPickerResult,
    categoryPickerResults: CategoryPickerResult
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel =

    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()

    /*HandleEffects(
        effects = viewModel.effects,
        snackbarHostState = snackbarHostState,
        lazyListState = lazyListState
    )*/

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalDateRangePickerResult provides dateRangePickerResult,
        LocalFeedPickerResult provides feedPickerResult,
        LocalCategoryPickerResult provides categoryPickerResults
    ) {
        FeedBody(
            posts = state.posts,
            lazyListState = lazyListState,
            rootPaddingValues = rootPaddingValues,
            snackbarHostState = snackbarHostState,
            paginationState = state.paginationState,
            isSwipeRefreshing = state.isSwipeRefreshing,
            isSearchBarActive = state.isSearchBarActive,
            initialFirstVisibleItemIndex = state.firstVisibleItemIndex,
            initialFirstVisibleItemOffset = state.firstVisibleItemOffset,
            onPostClick = { /*viewModel.openPost(it)*/ },
            onPostSaveClick = { /*viewModel.savePost(it)*/ },
            onPostLoadImage = { /*viewModel.loadMetadataIfNeeds(it)*/ },
            onManageFeedClick = { /*navController.navigate(ManageFeedScreenDestination())*/ },
            onRefreshClick = { /*viewModel.loadPosts(it)*/ },
            loadMorePosts = { /*viewModel.loadMorePosts()*/ },
            onSearchBarActiveChange = { /*viewModel.onSearchBarActiveChange(it)*/ },
            onScroll = { index, offset -> /*viewModel.onScroll(index, offset)*/ }
        )
    }
}

@Composable
private fun HandleEffects(
    effects: Flow<FeedEffect>,
    snackbarHostState: SnackbarHostState,
    lazyListState: LazyListState,
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

            is FeedEffect.ShowInvalidLinkMessage -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(
                        StringResources.feed_invalid_link
                    )
                )
            }

            is FeedEffect.ScrollToTop -> {
                lazyListState.animateScrollToItem(0)
            }
        }
    }
}
