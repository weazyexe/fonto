package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.OnlyBookmarksFilter
import dev.weazyexe.fonto.common.feature.newsline.PostDates
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DAY_MONTH_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState

@Stable
@Composable
fun List<NewslineFilter>.asViewStates(): List<FilterViewState<NewslineFilter>> = map {
    FilterViewState(
        filter = it,
        title = when (it) {
            is OnlyBookmarksFilter -> stringResource(R.string.feed_filters_bookmarks)
            is PostDates -> when (val range = it.range) {
                null -> stringResource(id = R.string.feed_filters_dates)
                else -> stringResource(
                    id = R.string.feed_filters_dates_value,
                    range.from.format(HUMAN_READABLE_DAY_MONTH_FORMAT),
                    range.to.format(HUMAN_READABLE_DAY_MONTH_FORMAT),
                )
            }
        }
    )
}