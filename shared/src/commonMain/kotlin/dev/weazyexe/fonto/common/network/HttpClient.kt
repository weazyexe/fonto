package dev.weazyexe.fonto.common.network

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.logging.*

fun createHttpClient(): HttpClient = HttpClient {
    install(Logging) {
        logger = object: Logger {
            override fun log(message: String) {
                Napier.v("HTTP Client", null, message)
            }
        }
        level = LogLevel.HEADERS
    }
}