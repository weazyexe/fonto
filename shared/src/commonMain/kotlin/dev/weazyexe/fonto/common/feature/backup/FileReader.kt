package dev.weazyexe.fonto.common.feature.backup

interface FileReader {

    suspend fun read(): ByteArray
}