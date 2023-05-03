package dev.weazyexe.fonto.debug

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import kotlinx.coroutines.launch

class DebugViewModel(
    private val deleteAllFeeds: DeleteAllFeedsUseCase,
    private val createFeed: CreateFeedUseCase,
    private val eventBus: EventBus
) : CoreViewModel<DebugState, DebugEffect>() {

    override val initialState: DebugState = DebugState()

    fun addMockFeeds(feed: List<Feed>) = viewModelScope.launch {
        deleteAllFeeds()

        feed.forEach {
            createFeed(it.title, it.link, it.icon, it.type)
        }

        DebugEffect.ShowMessage(R.string.debug_feed_storage_updated).emit()
        eventBus.emit(AppEvent.RefreshFeed)
    }
}