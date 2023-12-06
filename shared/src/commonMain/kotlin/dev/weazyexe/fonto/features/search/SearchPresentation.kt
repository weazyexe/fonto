package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.model.feed.Post

abstract class SearchPresentation : Presentation<SearchDomainState, SearchEffect>() {

    abstract fun onQueryChange(query: String)

    abstract fun applyFilters(updatedFilter: PostsFilter)

    abstract fun savePost(id: Post.Id)

    abstract fun openPost(id: Post.Id)

    abstract fun loadPostMetadataIfNeeds(id: Post.Id)

    abstract fun emit(effect: SearchEffect)
}
