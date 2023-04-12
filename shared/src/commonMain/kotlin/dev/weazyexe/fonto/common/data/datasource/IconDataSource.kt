package dev.weazyexe.fonto.common.data.datasource

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class IconDataSource(private val client: HttpClient) {

    suspend fun loadIcon(url: String): ByteArray {
        val iconResponse = client.get(url)
        return iconResponse.readBytes()
    }
}