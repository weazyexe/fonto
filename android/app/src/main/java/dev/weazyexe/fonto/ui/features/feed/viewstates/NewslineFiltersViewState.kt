package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.OnlyBookmarksFilter
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState

@Stable
@Composable
fun List<NewslineFilter>.asViewStates(): List<FilterViewState<NewslineFilter>> = map {
    FilterViewState(
        filter = it,
        title = stringResource(
            when (it) {
                is OnlyBookmarksFilter -> R.string.feed_filters_bookmarks
            }
        )
    )
}