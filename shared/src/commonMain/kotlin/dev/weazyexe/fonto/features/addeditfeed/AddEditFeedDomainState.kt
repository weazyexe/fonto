package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

data class AddEditFeedDomainState(
    val id: Feed.Id? = null,
    val title: String = "",
    val link: String = "",
    val type: Feed.Type = Feed.Type.RSS,
    val category: Category? = null,
    val categories: List<Category> = emptyList(),
    val icon: AsyncResult<LocalImage?> = AsyncResult.Success(null),
    val finishResult: AsyncResult<Unit> = AsyncResult.Success(Unit)
) : DomainState