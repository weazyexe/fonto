package dev.weazyexe.fonto.common.data.usecase.icon

import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.utils.getHostnameWithScheme

class GetIconByRssUrlUseCase(
    private val iconRepository: IconRepository
) {

    suspend operator fun invoke(rssUrl: String): LocalImage {
        val url = "${rssUrl.getHostnameWithScheme()}/favicon.ico"
        return iconRepository.loadIcon(url)
    }
}