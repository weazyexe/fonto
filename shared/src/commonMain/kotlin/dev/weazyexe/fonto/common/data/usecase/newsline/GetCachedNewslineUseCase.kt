package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetCachedNewslineUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke(): Newsline =
        Newsline(
            posts = newslineRepository.getAll()
        )
}