package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.NewLoadState
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asNewslineViewState
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeed: GetFeedUseCase,
    private val getNewsline: GetNewslineUseCase
) : CoreViewModel<FeedState, FeedEffect>() {

    override val initialState: FeedState = FeedState()

    init {
        loadNewsline()
    }

    fun loadNewsline() = viewModelScope.launch {
        setState { copy(newslineLoadState = NewLoadState.Loading()) }

        val feeds = request { getFeed() }
            .withErrorHandling {
                setState { copy(newslineLoadState = NewLoadState.Error(it)) }
            } ?: return@launch

        val newsline = request { getNewsline(feeds.data) }
            .withErrorHandling {
                setState { copy(newslineLoadState = NewLoadState.Error(it)) }
            } ?: return@launch

        setState { copy(newslineLoadState = newsline.asViewState { it.asNewslineViewState() }) }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }
}