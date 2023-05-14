package dev.weazyexe.fonto.common.feature.newsline

import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Filter

sealed interface NewslineFilter : Filter

data class OnlyBookmarksFilter(
    override val isEnabled: Boolean
) : Bool<OnlyBookmarksFilter>, NewslineFilter {

    override fun toggle(): OnlyBookmarksFilter {
        return OnlyBookmarksFilter(!isEnabled)
    }
}

val NewslineFilters = listOf<NewslineFilter>(
    OnlyBookmarksFilter(isEnabled = false)
)