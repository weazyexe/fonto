package dev.weazyexe.fonto.features.notifications

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Feed

data class NotificationsDomainState(
    val feeds: AsyncResult<List<Feed>> = AsyncResult.Loading()
) : DomainState