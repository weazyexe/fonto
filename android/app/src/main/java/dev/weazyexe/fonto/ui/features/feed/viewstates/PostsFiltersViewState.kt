package dev.weazyexe.fonto.ui.features.feed.viewstates

import android.content.Context
import dev.weazyexe.fonto.common.feature.filter.Multiple
import dev.weazyexe.fonto.common.feature.posts.ByCategory
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import dev.weazyexe.fonto.common.feature.posts.ByPostDates
import dev.weazyexe.fonto.common.feature.posts.ByRead
import dev.weazyexe.fonto.common.feature.posts.BySaved
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState
import dev.weazyexe.fonto.core.ui.utils.StringResources

fun List<PostsFilter>.asViewStates(context: Context): List<FilterViewState<PostsFilter>> = map { filter ->
    FilterViewState(
        filter = filter,
        title = when (filter) {
            is BySaved -> context.getString(StringResources.feed_filters_bookmarks)
            is ByRead -> context.getString(StringResources.feed_filters_read)
            is ByPostDates -> {
                val range = filter.range
                when {
                    range == null -> context.getString(StringResources.feed_filters_dates)

                    range.from == range.to -> range.from.format(HUMAN_READABLE_DATE_FORMAT)

                    else -> context.getString(
                        StringResources.feed_filters_dates_value,
                        range.from.format(HUMAN_READABLE_DATE_FORMAT),
                        range.to.format(HUMAN_READABLE_DATE_FORMAT),
                    )
                }
            }

            is ByFeed -> filter.formatMultipleFilterTitle(
                zeroValue = context.getString(StringResources.feed_filters_sources),
                getTitle = { it.title },
                context = context
            )

            is ByCategory -> filter.formatMultipleFilterTitle(
                zeroValue = context.getString(StringResources.feed_filters_categories),
                getTitle = { it.title },
                context = context
            )
        }
    )
}

private fun <T : Any> Multiple<T, *>.formatMultipleFilterTitle(
    zeroValue: String,
    getTitle: (T) -> String,
    context: Context
): String =
    when (val count = values.size) {
        0 -> zeroValue
        1 -> getTitle(values.first())
        2 -> context.getString(
            StringResources.feed_filters_quantity_two,
            getTitle(values[0]),
            getTitle(values[1]),
        )

        else -> context.getString(
            StringResources.feed_filters_quantity_multiple,
            getTitle(values[0]),
            getTitle(values[1]),
            count - 2
        )
    }