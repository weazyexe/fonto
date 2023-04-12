package dev.weazyexe.fonto.common.utils

import android.util.Patterns
import java.net.URI

actual fun String.getHostnameWithScheme(): String {
    val uri = URI(this)
    return uri.scheme + "://" + uri.host
}

actual fun String.isUrlValid(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}