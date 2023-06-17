package dev.weazyexe.fonto.common.data

/**
 * Exception wrapper
 */
sealed class ResponseError : Throwable() {

    object UnknownError : ResponseError()

    object NoInternetError : ResponseError()

    object TimeoutError : ResponseError()

    object InvalidRssFeed : ResponseError()

    object InvalidTitle : ResponseError()
}