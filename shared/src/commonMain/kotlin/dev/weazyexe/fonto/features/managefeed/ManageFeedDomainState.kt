package dev.weazyexe.fonto.features.managefeed

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Feed

data class ManageFeedDomainState(
    val feeds: AsyncResult<List<Feed>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
) : DomainState
