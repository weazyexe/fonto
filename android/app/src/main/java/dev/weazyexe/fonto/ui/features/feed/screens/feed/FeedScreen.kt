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
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.browser.InAppBrowser
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerArgs
import dev.weazyexe.fonto.ui.features.home.dependencies.DateRangePickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.FeedPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ManageFeedResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    rootPaddingValues: PaddingValues,
    viewModel: FeedViewModel,
    navigateTo: NavigateTo,
    manageFeedResultRecipientProvider: ManageFeedResults,
    dateRangePickerResultRecipientProvider: DateRangePickerResults,
    feedPickerResults: FeedPickerResults
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    manageFeedResultRecipientProvider.invoke().onNavResult { result ->
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

    dateRangePickerResultRecipientProvider.invoke().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                result.value?.let {
                    viewModel.applyFilters(
                        ByPostDates(
                            range = Dates.Range(
                                from = it.from,
                                to = it.to
                            )
                        )
                    )
                }
            }
        }
    }

    feedPickerResults.invoke().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                result.value?.let { result ->
                    viewModel.applyFilters(
                        ByFeed(
                            values = result.values,
                            possibleValues = result.possibleValues
                        )
                    )
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

            is FeedEffect.OpenFeedPicker -> {
                navigateTo(
                    FeedPickerDialogDestination(
                        args = FeedPickerArgs(
                            values = values,
                            possibleValues = possibleValues,
                            title = title
                        )
                    )
                )
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
        onPostSaveClick = viewModel::savePost,
        onScroll = viewModel::onScroll,
        onManageFeedClick = { navigateTo(ManageFeedScreenDestination()) },
        onSearchClick = {},
        onRefreshClick = viewModel::loadNewsline,
        fetchNextBatch = viewModel::getNextPostsBatch,
        onFilterChange = viewModel::applyFilters,
        openDateRangePickerDialog = { navigateTo(DateRangePickerDialogDestination()) },
        openMultiplePickerDialog = viewModel::openMultipleValuePicker
    )
}
