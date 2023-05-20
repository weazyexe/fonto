package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.annotation.StringRes
import dev.weazyexe.fonto.BuildConfig
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

data class FeedState(
    val newslineLoadState: LoadState<NewslineViewState> = LoadState.Loading(),
    val newslinePaginationState: PaginationState = PaginationState.IDLE,
    val scrollState: ScrollState = ScrollState(),
    val isSwipeRefreshing: Boolean = false,
    val limit: Int = DEFAULT_LIMIT,
    val offset: Int = 0,
    val filters: List<NewslineFilter>? = null,
    val isBenchmarking: Boolean = BuildConfig.BUILD_TYPE == "benchmark"
) : State

sealed interface FeedEffect : Effect {

    class ShowMessage(@StringRes val message: Int, vararg val args: Any) : FeedEffect

    data class OpenPostInApp(
        val link: String,
        val theme: Theme
    ) : FeedEffect

    data class OpenPostInBrowser(val link: String) : FeedEffect

    data class OpenFeedPicker(
        val values: List<ByFeed.Data>,
        val possibleValues: List<ByFeed.Data>,
        @StringRes val title: Int
    ) : FeedEffect

    data class OpenSourcePicker(
        val values: List<Category>,
        val possibleValues: List<Category>,
        @StringRes val title: Int
    ) : FeedEffect
}
