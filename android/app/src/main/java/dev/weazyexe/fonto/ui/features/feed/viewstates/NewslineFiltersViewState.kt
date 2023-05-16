package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.OnlyBookmarksFilter
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_FORMAT
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
            is ByPostDates -> {
                val range = it.range
                when {
                    range == null -> stringResource(id = R.string.feed_filters_dates)

                    range.from == range.to -> range.from.format(HUMAN_READABLE_DATE_FORMAT)

                    else -> stringResource(
                        id = R.string.feed_filters_dates_value,
                        range.from.format(HUMAN_READABLE_DATE_FORMAT),
                        range.to.format(HUMAN_READABLE_DATE_FORMAT),
                    )
                }
            }

            is ByFeed -> when (val count = it.values.size) {
                0 -> stringResource(id = R.string.feed_filters_sources)
                1 -> it.values.first().title
                2 -> stringResource(
                    id = R.string.feed_filters_sources_two,
                    it.values[0].title,
                    it.values[1].title
                )

                else -> stringResource(
                    id = R.string.feed_filters_sources_multiple,
                    it.values[0],
                    it.values[1],
                    count - 2
                )
            }
        }
    )
}