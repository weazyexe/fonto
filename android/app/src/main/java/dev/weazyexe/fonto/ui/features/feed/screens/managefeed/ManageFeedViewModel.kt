package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewState
import kotlinx.coroutines.launch

class ManageFeedViewModel(
    private val getFeed: GetFeedUseCase,
    private val deleteFeed: DeleteFeedUseCase
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

    fun deleteFeedById(id: Long) = viewModelScope.launch {
        val response = request { deleteFeed(id) }

        if (response.error != null || response.data == null) {
            ManageFeedEffect.ShowMessage(R.string.error_feed_can_not_delete_feed).emit()
            return@launch
        }

        ManageFeedEffect.ShowMessage(R.string.error_feed_deleted_successfully).emit()
        loadFeed()
    }

    fun showSavedMessage() {
        ManageFeedEffect.ShowMessage(R.string.manage_feed_changes_saved).emit()
    }
}