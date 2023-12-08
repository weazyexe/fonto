package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.html.OgMetadata
import dev.weazyexe.fonto.common.html.OgMetadataExtractor
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.utils.extensions.flowIo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow

interface GetPostMetadataFromHtmlUseCase {

    operator fun invoke(link: String): Flow<AsyncResult<OgMetadata>>
}

internal class GetPostMetadataFromHtmlUseCaseImpl(
    private val httpClient: HttpClient,
    private val ogMetadataExtractor: OgMetadataExtractor
) : GetPostMetadataFromHtmlUseCase {

    override operator fun invoke(link: String): Flow<AsyncResult<OgMetadata>> = flowIo {
        emit(AsyncResult.Loading())
        val html = httpClient.get(link).bodyAsText()
        val metadata = ogMetadataExtractor.extract(html)
        emit(
            AsyncResult.Success(
                metadata.copy(description = metadata.description?.cleanUpText())
            )
        )
    }
}
