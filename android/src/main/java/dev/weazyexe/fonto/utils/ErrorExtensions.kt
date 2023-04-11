package dev.weazyexe.fonto.utils

import coil.network.HttpException
import dev.weazyexe.fonto.ui.core.presentation.ResponseError
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Exception.asResponseError(): ResponseError {
    return when (this) {
        is UnknownHostException,
        is ConnectException -> ResponseError.NoInternetError()
        is TimeoutException -> ResponseError.TimeoutError()

        is HttpException -> {
            ResponseError.HttpError(arguments = listOf(message.orEmpty()))
        }

        else -> {
            ResponseError.UnknownError()
        }
    }
}