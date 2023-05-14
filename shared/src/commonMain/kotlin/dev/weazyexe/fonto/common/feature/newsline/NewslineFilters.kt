package dev.weazyexe.fonto.common.feature.newsline

import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.filter.Filter
import dev.weazyexe.fonto.common.feature.filter.Multiple
import dev.weazyexe.fonto.common.model.feed.Feed

sealed interface NewslineFilter : Filter

data class OnlyBookmarksFilter(
    override val isEnabled: Boolean
) : Bool<OnlyBookmarksFilter>, NewslineFilter {

    override fun toggle(): OnlyBookmarksFilter {
        return OnlyBookmarksFilter(!isEnabled)
    }
}

data class ByPostDates(
    override val range: Dates.Range?
) : Dates<ByPostDates>, NewslineFilter {

    override fun change(range: Dates.Range?): ByPostDates {
        return ByPostDates(range)
    }
}

data class ByFeed(
    override val values: List<Feed.Id>,
    override val possibleValues: List<Feed.Id>
) : Multiple<Feed.Id, ByFeed>, NewslineFilter {

    override fun change(newValue: List<Feed.Id>, newPossibleValues: List<Feed.Id>): ByFeed {
        return ByFeed(newValue, newPossibleValues)
    }
}

val NewslineFilters = listOf(
    OnlyBookmarksFilter(isEnabled = false),
    ByPostDates(range = null),
    ByFeed(values = emptyList(), possibleValues = emptyList())
)