package dev.weazyexe.fonto.common.feature.backup

import android.content.Context
import android.net.Uri

class AndroidFileSaver(
    private val context: Context,
    private val uri: Uri
) : FileSaver {

    override suspend fun save(data: ByteArray) {
        context.contentResolver.openOutputStream(uri)?.use {
            it.write(data)
        }
    }
}