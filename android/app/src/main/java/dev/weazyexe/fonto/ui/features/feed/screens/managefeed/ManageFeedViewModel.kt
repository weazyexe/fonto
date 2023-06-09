package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.features.managefeed.ManageFeedDomainState
import dev.weazyexe.fonto.features.managefeed.ManageFeedPresentation
import dev.weazyexe.fonto.ui.features.feed.components.feed.asViewState
import kotlinx.coroutines.flow.map

class ManageFeedViewModel(private val presentation: ManageFeedPresentation) : ViewModel() {

    val state = presentation.domainState.map { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun loadFeed() {
        presentation.loadFeed()
    }

    fun deleteFeedById(id: Feed.Id) {
        presentation.deleteFeedById(id)
    }

    fun updateChangesStatus() {
        presentation.updateChangesStatus()
    }

    private fun ManageFeedDomainState.asViewState(): ManageFeedViewState {
        return ManageFeedViewState(
            feeds = feeds.map { feeds -> feeds.map { it.asViewState() } },
            hasChanges = hasChanges
        )
    }
}
