package dev.weazyexe.fonto.common.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes

internal class IconDataSource(private val client: HttpClient) {

    suspend fun loadIcon(url: String): ByteArray {
        val iconResponse = client.get(url)
        return iconResponse.readBytes()
    }
}