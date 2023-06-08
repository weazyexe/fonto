package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.ui.features.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.screens.categorypicker.CategoryPickerArgs
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalCategoryPickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalDateRangePickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalFeedPickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalNavigateTo
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerArgs
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchOverlay(
    isActive: Boolean,
    onSearchBarActiveChange: (Boolean) -> Unit,
    onPostClick: (Post.Id) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val viewModel = koinViewModel<SearchViewModel>()
    val state by viewModel.uiState.collectAsState()

    HandleEffects(viewModel.effects)

    HandleNavigationResults(
        onDateRangeReceived = viewModel::applyFilters,
        onFeedReceived = viewModel::applyFilters,
        onCategoryReceived = viewModel::applyFilters
    )

    SearchBody(
        query = state.query,
        postsLoadState = state.postsLoadState,
        filters = state.filters,
        isActive = isActive,
        areFiltersChanged = state.areFiltersChanged,
        contentPadding = contentPadding,
        onQueryChange = viewModel::onQueryChange,
        onSearch = { keyboardController?.hide() },
        onActiveChange = onSearchBarActiveChange,
        onFilterChange = viewModel::applyFilters,
        openDateRangePickerDialog = viewModel::openDateRangePicker,
        openMultiplePickerDialog = viewModel::openMultiplePicker,
        onPostClick = onPostClick,
        onPostSaveClick = {
            onPostSaveClick(it)
            viewModel.onPostSave(it)
        },
        modifier = modifier
    )
}

@Composable
private fun HandleEffects(effects: Flow<SearchEffect>) {
    val navigateTo = LocalNavigateTo.current
    ReceiveEffect(effects) {
        when (this) {
            is SearchEffect.OpenDateRangePicker -> {
                navigateTo?.invoke(DateRangePickerDialogDestination())
            }

            is SearchEffect.OpenFeedPicker -> {
                navigateTo?.invoke(
                    FeedPickerDialogDestination(
                        args = FeedPickerArgs(
                            values = values,
                            possibleValues = possibleValues,
                            title = title
                        )
                    )
                )
            }

            is SearchEffect.OpenCategoryPicker -> {
                navigateTo?.invoke(
                    CategoryPickerDialogDestination(
                        args = CategoryPickerArgs(
                            values = values,
                            possibleValues = possibleValues,
                            title = title
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun HandleNavigationResults(
    onDateRangeReceived: (ByPostDates) -> Unit,
    onFeedReceived: (ByFeed) -> Unit,
    onCategoryReceived: (ByCategory) -> Unit,
) {
    LocalDateRangePickerResults.current?.invoke()?.handleResults { result ->
        result?.let {
            onDateRangeReceived(
                ByPostDates(
                    range = Dates.Range(
                        from = it.from,
                        to = it.to
                    )
                )
            )
        }
    }

    LocalFeedPickerResults.current?.invoke()?.handleResults { result ->
        result?.let {
            onFeedReceived(
                ByFeed(
                    values = result.values,
                    possibleValues = result.possibleValues
                )
            )
        }
    }

    LocalCategoryPickerResults.current?.invoke()?.handleResults { result ->
        result?.let {
            onCategoryReceived(
                ByCategory(
                    values = result.values,
                    possibleValues = result.possibleValues
                )
            )
        }
    }
}