package dev.weazyexe.fonto.common.feature.newsline

import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.filter.Filter

sealed interface NewslineFilter : Filter

data class OnlyBookmarksFilter(
    override val isEnabled: Boolean
) : Bool<OnlyBookmarksFilter>, NewslineFilter {

    override fun toggle(): OnlyBookmarksFilter {
        return OnlyBookmarksFilter(!isEnabled)
    }
}

data class PostDates(
    override val range: Dates.Range?
) : Dates<PostDates>, NewslineFilter {

    override fun change(range: Dates.Range?): PostDates {
        return PostDates(range)
    }
}

val NewslineFilters = listOf(
    OnlyBookmarksFilter(isEnabled = false),
    PostDates(range = null)
)