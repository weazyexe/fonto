package dev.weazyexe.fonto.common.serialization

import kotlinx.serialization.json.Json

fun createJson(): Json = Json { ignoreUnknownKeys = true }