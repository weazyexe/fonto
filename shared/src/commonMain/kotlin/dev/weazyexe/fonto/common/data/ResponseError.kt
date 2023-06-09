package dev.weazyexe.fonto.common.data

/**
 * Exception wrapper
 */
sealed class ResponseError : Throwable() {

    object UnknownError : ResponseError()

    object NoInternetError : ResponseError()

    object TimeoutError : ResponseError()

    data class FeedValidationError(override val message: String) : ResponseError()

    object InvalidRssFeed : ResponseError()
}