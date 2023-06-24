package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.common.html.OgImageExtractor
import dev.weazyexe.fonto.utils.extensions.flowIo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow

internal class GetImageFromHtmlMetaUseCase(
    private val httpClient: HttpClient,
    private val ogImageExtractor: OgImageExtractor
) {

    operator fun invoke(link: String): Flow<AsyncResult<String>> = flowIo {
        emit(AsyncResult.Loading())
        val html = httpClient.get(link).bodyAsText()
        val image = ogImageExtractor.extract(html)

        emit(
            if (image != null) {
                AsyncResult.Success(image)
            } else {
                AsyncResult.Error(ResponseError.NoOgImageMeta)
            }
        )
    }
}