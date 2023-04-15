package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.ui.core.ScrollState
import dev.weazyexe.fonto.ui.core.presentation.CoreViewModel
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.ResponseError
import dev.weazyexe.fonto.ui.core.presentation.asViewState
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
        setState { copy(newslineLoadState = LoadState.loading()) }

        val feeds = request { getFeed() }
        if (feeds.hasError()) {
            setState {
                copy(
                    newslineLoadState = LoadState.error(
                        feeds.error ?: ResponseError.FetchFeedError()
                    )
                )
            }
            return@launch
        }

        val newsline = request {
            getNewsline(feeds.data.orEmpty())
        }.asViewState { it.asNewslineViewState() }

        if (newsline.hasError()) {
            setState {
                copy(
                    newslineLoadState = LoadState.error(
                        feeds.error ?: ResponseError.FetchNewslineError()
                    )
                )
            }
            return@launch
        }

        setState { copy(newslineLoadState = newsline) }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }
}