package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Post

class UpdatePostUseCase(
    private val newslineRepository: NewslineRepository
) {

    operator fun invoke(post: Post) {
        newslineRepository.insertOrUpdate(post)
    }
}