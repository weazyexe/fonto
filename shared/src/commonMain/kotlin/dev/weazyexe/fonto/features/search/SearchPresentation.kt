package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Post

abstract class SearchPresentation : Presentation<SearchDomainState, SearchEffect>() {

    abstract fun onQueryChange(query: String)

    abstract fun applyFilters(updatedFilter: NewslineFilter)

    abstract fun onPostRead(id: Post.Id)

    abstract fun onPostSave(id: Post.Id)

    abstract fun emit(effect: SearchEffect)
}