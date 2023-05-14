package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.filters.FiltersRow
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewStates
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedToolbar(
    filters: List<NewslineFilter>?,
    scrollBehavior: TopAppBarScrollBehavior,
    lazyListState: LazyListState,
    onFilterChange: (NewslineFilter) -> Unit,
    openDateRangePickerDialog: (NewslineFilter) -> Unit,
    openMultiplePickerDialog: (NewslineFilter) -> Unit,
    onSearchClick: () -> Unit,
    onManageFeedClick: () -> Unit,
    getTitleById: (Feed.Id) -> String
) {
    val scope = rememberCoroutineScope()
    val areFiltersVisible by remember {
        derivedStateOf {
            1f - scrollBehavior.state.collapsedFraction > 0.6f
        }
    }

    LargeTopAppBar(
        title = {
            Column {
                Text(text = stringResource(id = R.string.home_bottom_label_feed))
                filters?.let {
                    AnimatedVisibility(visible = areFiltersVisible) {
                        FiltersRow(
                            filters = filters.asViewStates(getTitleById),
                            onBoolFilterChange = { onFilterChange(it as NewslineFilter) },
                            openDateRangePickerDialog = {
                                openDateRangePickerDialog(it as NewslineFilter)
                            },
                            onClearDatesFilter = { onFilterChange(it as NewslineFilter) },
                            openMultiplePickerDialog = {
                                openMultiplePickerDialog(it as NewslineFilter)
                            },
                            onClearMultipleFilter = { onFilterChange(it as NewslineFilter) },
                            modifier = Modifier.alpha(1f - scrollBehavior.state.collapsedFraction),
                            startPadding = 0.dp
                        )
                    }
                }
            }
        },
        modifier = Modifier.clickable(
            onClick = {
                scope.launch { lazyListState.animateScrollToItem(0) }
            },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ),
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = null
                )
            }
            IconButton(onClick = onManageFeedClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_controls_24),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}