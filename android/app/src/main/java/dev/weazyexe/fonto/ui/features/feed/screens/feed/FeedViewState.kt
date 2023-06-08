package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostsViewState

@Immutable
data class FeedViewState(
    val posts: AsyncResult<PostsViewState> = AsyncResult.Loading(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val scrollState: ScrollState = ScrollState(),
    val isSwipeRefreshing: Boolean = false,
    val isSearchBarActive: Boolean = false
) : State

sealed interface FeedEffect : Effect {

    class ShowMessage(@StringRes val message: Int, vararg val args: Any) : FeedEffect

    data class OpenPostInApp(
        val link: String,
        val theme: Theme
    ) : FeedEffect

    data class OpenPostInBrowser(val link: String) : FeedEffect
}
