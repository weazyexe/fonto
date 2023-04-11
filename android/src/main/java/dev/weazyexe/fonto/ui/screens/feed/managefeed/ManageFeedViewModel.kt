package dev.weazyexe.fonto.ui.screens.feed.managefeed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.GetFeedUseCase
import dev.weazyexe.fonto.ui.core.presentation.CoreViewModel
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.asViewState
import dev.weazyexe.fonto.ui.screens.feed.managefeed.viewstates.asViewState
import kotlinx.coroutines.launch

class ManageFeedViewModel(
    private val getFeed: GetFeedUseCase
) : CoreViewModel<ManageFeedState, ManageFeedEffect>() {

    override val initialState: ManageFeedState = ManageFeedState()

    init {
        loadFeed()
    }

    fun loadFeed() = viewModelScope.launch {
        setState { copy(feedLoadState = LoadState.loading()) }
        val feedResponse = request { getFeed() }
        val preparedViewState = feedResponse.asViewState { data ->
            data.map { it.asViewState() }
        }
        setState { copy(feedLoadState = preparedViewState) }
    }
}