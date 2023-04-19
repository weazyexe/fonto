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
        setState { copy(feedLoadState = LoadState.Loading.Default()) }
        val feedResponse = request { getFeed() }
            .withErrorHandling {
                setState { copy(feedLoadState = LoadState.Error(it)) }
            } ?: return@launch

        val preparedViewState = feedResponse.asViewState { data ->
            data.map { it.asViewState() }
        }
        setState { copy(feedLoadState = preparedViewState) }
    }

    fun deleteFeedById(id: Long) = viewModelScope.launch {
        request { deleteFeed(id) }
            .withErrorHandling {
                ManageFeedEffect.ShowMessage(R.string.error_feed_can_not_delete_feed).emit()
            } ?: return@launch

        ManageFeedEffect.ShowMessage(R.string.error_feed_deleted_successfully).emit()
        updateChangesStatus()
        loadFeed()
    }

    fun updateChangesStatus() {
        setState { copy(hasChanges = true) }
    }

    fun showSavedMessage() {
        ManageFeedEffect.ShowMessage(R.string.manage_feed_changes_saved).emit()
    }
}