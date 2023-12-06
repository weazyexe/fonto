package dev.weazyexe.fonto.features.categories

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

data class CategoriesDomainState(
    val categories: AsyncResult<List<Category>> = AsyncResult.Loading(),
    val feeds: List<Feed> = emptyList(),
    val hasChanges: Boolean = false
) : DomainState
