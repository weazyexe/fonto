package dev.weazyexe.fonto.common.feature.backup

import android.content.Context
import android.net.Uri

class AndroidFileReader(
    private val context: Context,
    private val uri: Uri
) : FileReader {

    override suspend fun read(): ByteArray {
        context.contentResolver.openInputStream(uri)?.use {
            return it.readBytes()
        } ?: run {
            throw IllegalStateException("Can't read file")
        }
    }
}