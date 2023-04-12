package dev.weazyexe.fonto.common.data.usecase

import dev.weazyexe.fonto.common.data.repository.NewslineRepository

class IsNewslineValidUseCase(private val newslineRepository: NewslineRepository) {

    suspend operator fun invoke(url: String): Boolean {
        return newslineRepository.getNewsline(url).posts.isNotEmpty()
    }
}