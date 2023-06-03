package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter

class GetFiltersUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke() : List<NewslineFilter> = postRepository.composeFilters()
}