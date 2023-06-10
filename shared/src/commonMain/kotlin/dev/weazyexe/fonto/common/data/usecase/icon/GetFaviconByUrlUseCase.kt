package dev.weazyexe.fonto.common.data.usecase.icon

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.utils.getHostnameWithScheme
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.flow.Flow

class GetFaviconByUrlUseCase(
    private val iconRepository: IconRepository
) {

    operator fun invoke(rssUrl: String): Flow<AsyncResult<LocalImage>> = flowIo {
        emit(AsyncResult.Loading())
        val url = "${rssUrl.getHostnameWithScheme()}/favicon.ico"


        emit(AsyncResult.Success(iconRepository.loadIcon(url)))
    }
}