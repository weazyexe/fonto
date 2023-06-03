package dev.weazyexe.fonto.common.feature.backup

interface FileSaver {

    suspend fun save(data: ByteArray)
}