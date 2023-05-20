package dev.weazyexe.fonto.common.resources

import android.content.Context

actual class StringsProviderFactory(
    private val context: Context
) {

    actual fun create(): StringsProvider {
        return AndroidStringsProvider(context)
    }
}