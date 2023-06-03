package dev.weazyexe.fonto.common.data.usecase.icon

import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.utils.getHostnameWithScheme

class GetFaviconByUrlUseCase(
    private val iconRepository: IconRepository
) {

    suspend operator fun invoke(rssUrl: String): LocalImage? = try {
        val url = "${rssUrl.getHostnameWithScheme()}/favicon.ico"
        iconRepository.loadIcon(url)
    } catch (e: Exception) {
        null
    }
}