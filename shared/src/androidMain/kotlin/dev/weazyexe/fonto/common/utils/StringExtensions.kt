package dev.weazyexe.fonto.common.utils

import android.text.Html
import android.util.Patterns
import androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
import androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
import java.net.URI


actual fun String.getHostnameWithScheme(): String {
    val uri = URI(this)
    return uri.scheme + "://" + uri.host
}

actual fun String.isUrlValid(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}

actual fun String.fixHtmlEntities(): String {
    val flags = FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH and FROM_HTML_SEPARATOR_LINE_BREAK_DIV
    return Html.fromHtml(this, flags).toString()
}