package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.usecase.newsline.GetPostsUseCase

data class FeedDependencies(
    val initialState: FeedDomainState,
    val getPosts: GetPostsUseCase
)