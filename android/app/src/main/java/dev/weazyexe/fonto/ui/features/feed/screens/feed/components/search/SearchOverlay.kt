package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.posts.ByCategory
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import dev.weazyexe.fonto.common.feature.posts.ByPostDates
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.search.SearchEffect
import dev.weazyexe.fonto.ui.features.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.categorypicker.CategoryPickerArgs
import dev.weazyexe.fonto.ui.features.feed.screens.feed.browser.InAppBrowser
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalCategoryPickerResult
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalDateRangePickerResult
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalFeedPickerResult
import dev.weazyexe.fonto.ui.features.feed.screens.feed.composition.LocalNavController
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerArgs
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchOverlay(
    isActive: Boolean,
    onSearchBarActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val viewModel = koinViewModel<SearchViewModel>()
    val state by viewModel.state.collectAsState(SearchViewState())
    val navController = LocalNavController.current

    HandleEffects(
        effects = viewModel.effects,
        navController = navController,
        snackbarHostState = snackbarHostState
    )

    HandleNavigationResults(
        onDateRangeReceived = viewModel::applyFilters,
        onFeedReceived = viewModel::applyFilters,
        onCategoryReceived = viewModel::applyFilters
    )

    SearchBody(
        query = state.query,
        posts = state.posts,
        filters = state.filters,
        isActive = isActive,
        areFiltersChanged = state.areFiltersChanged,
        contentPadding = contentPadding,
        snackbarHostState = snackbarHostState,
        onQueryChange = viewModel::onQueryChange,
        onSearch = { keyboardController?.hide() },
        onActiveChange = onSearchBarActiveChange,
        onFilterChange = viewModel::applyFilters,
        openDateRangePickerDialog = { navController.navigate(DateRangePickerDialogDestination()) },
        openMultiplePickerDialog = { viewModel.openMultiplePicker(it) },
        onPostClick = { viewModel.openPost(it) },
        onPostSaveClick = { viewModel.savePost(it) },
        loadPostMetadata = { viewModel.loadPostMetadataIfNeeds(it) },
        modifier = modifier
    )
}

@Composable
private fun HandleEffects(
    effects: Flow<SearchEffect>,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    ReceiveEffect(effects) {
        when (this) {
            is SearchEffect.OpenFeedPicker -> {
                navController.navigate(
                    FeedPickerDialogDestination(
                        args = FeedPickerArgs(
                            values = values,
                            possibleValues = possibleValues,
                            title = StringResources.feed_filters_sources
                        )
                    )
                )
            }

            is SearchEffect.OpenCategoryPicker -> {
                navController.navigate(
                    CategoryPickerDialogDestination(
                        args = CategoryPickerArgs(
                            values = values,
                            possibleValues = possibleValues,
                            title = StringResources.feed_filters_categories
                        )
                    )
                )
            }

            is SearchEffect.OpenPostInApp -> {
                InAppBrowser.openPost(context, link, theme)
            }

            is SearchEffect.OpenPostInBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }

            is SearchEffect.ShowPostSavingErrorMessage -> {
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

            is SearchEffect.ShowPostSavedMessage -> {
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

            is SearchEffect.ShowInvalidLinkMessage -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(
                        StringResources.feed_invalid_link
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
    LocalDateRangePickerResult.current.handleResults { result ->
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

    LocalFeedPickerResult.current.handleResults { result ->
        result?.let {
            onFeedReceived(
                ByFeed(
                    values = result.values,
                    possibleValues = result.possibleValues
                )
            )
        }
    }

    LocalCategoryPickerResult.current.handleResults { result ->
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