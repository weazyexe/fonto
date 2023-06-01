package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.feature.filter.Multiple
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.ByRead
import dev.weazyexe.fonto.common.feature.newsline.BySaved
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Stable
@Composable
fun List<NewslineFilter>.asViewStates(): List<FilterViewState<NewslineFilter>> = map { filter ->
    FilterViewState(
        filter = filter,
        title = when (filter) {
            is BySaved -> stringResource(StringResources.feed_filters_bookmarks)
            is ByRead -> stringResource(StringResources.feed_filters_read)
            is ByPostDates -> {
                val range = filter.range
                when {
                    range == null -> stringResource(id = StringResources.feed_filters_dates)

                    range.from == range.to -> range.from.format(HUMAN_READABLE_DATE_FORMAT)

                    else -> stringResource(
                        id = StringResources.feed_filters_dates_value,
                        range.from.format(HUMAN_READABLE_DATE_FORMAT),
                        range.to.format(HUMAN_READABLE_DATE_FORMAT),
                    )
                }
            }

            is ByFeed -> filter.formatMultipleFilterTitle(
                zeroValue = stringResource(id = StringResources.feed_filters_sources),
                getTitle = { it.title }
            )

            is ByCategory -> filter.formatMultipleFilterTitle(
                zeroValue = stringResource(id = StringResources.feed_filters_categories),
                getTitle = { it.title }
            )
        }
    )
}

@Stable
@Composable
private fun <T : Any> Multiple<T, *>.formatMultipleFilterTitle(
    zeroValue: String,
    getTitle: (T) -> String
): String =
    when (val count = values.size) {
        0 -> zeroValue
        1 -> getTitle(values.first())
        2 -> stringResource(
            id = StringResources.feed_filters_quantity_two,
            getTitle(values[0]),
            getTitle(values[1]),
        )

        else -> stringResource(
            id = StringResources.feed_filters_quantity_multiple,
            getTitle(values[0]),
            getTitle(values[1]),
            count - 2
        )
    }