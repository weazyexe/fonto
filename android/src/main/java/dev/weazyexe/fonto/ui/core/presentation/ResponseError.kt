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

    fun asLocalizedMessage(context: Context): String {
        return context.getString(errorMessage, *arguments.toTypedArray())
    }
}