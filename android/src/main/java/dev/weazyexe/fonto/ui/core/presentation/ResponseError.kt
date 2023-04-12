package dev.weazyexe.fonto.ui.core.presentation

import android.content.Context
import androidx.annotation.StringRes
import dev.weazyexe.fonto.R

/**
 * Exception wrapper
 */
sealed class ResponseError(
    @StringRes open val errorMessage: Int,
    open val arguments: Collection<Any>
) : Throwable() {

    data class UnknownError(
        override val errorMessage: Int = R.string.error_unknown
    ) : ResponseError(errorMessage, emptyList())

    data class NoInternetError(
        override val errorMessage: Int = R.string.error_no_internet
    ) : ResponseError(errorMessage, emptyList())

    data class TimeoutError(
        override val errorMessage: Int = R.string.error_timed_out
    ) : ResponseError(errorMessage, emptyList())

    data class HttpError(
        override val errorMessage: Int = R.string.error_http,
        override val arguments: Collection<Any>
    ) : ResponseError(errorMessage, arguments)

    data class FeedIconError(
        override val errorMessage: Int = R.string.error_feed_icon_error
    ) : ResponseError(errorMessage, emptyList())

    data class InvalidRssFeed(
        override val errorMessage: Int = R.string.error_invalid_rss_feed
    ) : ResponseError(errorMessage, emptyList())

    data class FeedValidationError(
        override val errorMessage: Int
    ) : ResponseError(errorMessage, emptyList())

    fun asLocalizedMessage(context: Context): String {
        return context.getString(errorMessage, *arguments.toTypedArray())
    }
}