package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.core.ui.utils.asResponseError
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

    fun loadNewsline(isSwipeRefreshed: Boolean = false) = viewModelScope.launch {
        setState {
            copy(
                newslineLoadState = if (isSwipeRefreshed) {
                    LoadState.Loading.SwipeRefresh(
                        data = (newslineLoadState as? LoadState.Data)?.data
                    )
                } else {
                    LoadState.Loading.Default()
                },
                scrollState = ScrollState()
            )
        }

        val feeds = request { getFeed() }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return@launch

        val newsline = request { getNewsline(feeds.data) }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return@launch

        if (newsline.data.loadedWithError.isNotEmpty() && newsline.data.loadedWithError.size == feeds.data.size) {
            val error = newsline.data.loadedWithError.first().throwable.asResponseError()
            setState { copy(newslineLoadState = LoadState.Error(error)) }
        } else {
            setState { copy(newslineLoadState = newsline.asViewState { it.asNewslineViewState() }) }
            showNotLoadedSourcesError(newsline.data.loadedWithError.map { it.feed })
        }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }

    private fun showNotLoadedSourcesError(problematicFeedList: List<Feed>) {
        if (problematicFeedList.isNotEmpty()) {
            val feedListString = problematicFeedList.joinToString { it.title }
            FeedEffect.ShowMessage(R.string.feed_error_sources, feedListString).emit()
        }
    }
}