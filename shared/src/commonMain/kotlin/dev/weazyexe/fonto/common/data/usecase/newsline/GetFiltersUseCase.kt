package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter

class GetFiltersUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke() : List<NewslineFilter> = newslineRepository.composeFilters()
}