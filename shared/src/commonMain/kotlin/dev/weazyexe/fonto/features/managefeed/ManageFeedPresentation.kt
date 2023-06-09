package dev.weazyexe.fonto.features.managefeed

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.model.feed.Feed

abstract class ManageFeedPresentation : Presentation<ManageFeedDomainState, ManageFeedEffect>() {

    abstract fun loadFeed()

    abstract fun deleteFeedById(id: Feed.Id)

    abstract fun updateChangesStatus()
}