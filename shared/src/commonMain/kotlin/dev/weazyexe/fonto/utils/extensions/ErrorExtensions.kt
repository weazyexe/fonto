package dev.weazyexe.fonto.utils.extensions

import dev.weazyexe.fonto.common.data.ResponseError
import io.github.aakira.napier.Napier
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

internal fun Throwable.asResponseError(): ResponseError {
    Napier.e(throwable = this, message = message ?: "Error")
    return when (this) {
        is UnknownHostException,
        is ConnectException -> ResponseError.NoInternetError

        is TimeoutException -> ResponseError.TimeoutError

        else -> {
            ResponseError.UnknownError
        }
    }
}