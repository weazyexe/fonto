package dev.weazyexe.fonto.common.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed interface AsyncResult<out T> {

    open class Loading<out T> : AsyncResult<T> {

        data class Progress<T>(
            val current: Int,
            val total: Int
        ) : Loading<T>()
    }

    data class Success<T>(val data: T) : AsyncResult<T>

    data class Error<T>(
        val error: ResponseError
    ) : AsyncResult<T>
}

fun <T, R> AsyncResult<T>.map(transform: (T) -> R): AsyncResult<R> =
    when (this) {
        is AsyncResult.Loading.Progress -> AsyncResult.Loading.Progress(current, total)
        is AsyncResult.Loading -> AsyncResult.Loading()
        is AsyncResult.Success -> AsyncResult.Success(transform(data))
        is AsyncResult.Error -> AsyncResult.Error(error)
    }

fun <T> Flow<AsyncResult<T>>.onSuccess(block: suspend (AsyncResult.Success<T>) -> Unit): Flow<AsyncResult<T>> =
    onEach {
        if (it is AsyncResult.Success) {
            block(it)
        }
    }

fun <T> Flow<AsyncResult<T>>.onError(block: (AsyncResult.Error<T>) -> Unit): Flow<AsyncResult<T>> =
    onEach {
        if (it is AsyncResult.Error) {
            block(it)
        }
    }

fun <T> Flow<AsyncResult<T>>.onLoading(block: (AsyncResult.Loading<T>) -> Unit): Flow<AsyncResult<T>> =
    onEach {
        if (it is AsyncResult.Loading && it !is AsyncResult.Loading.Progress) {
            block(it)
        }
    }

fun <T> Flow<AsyncResult<T>>.onProgress(block: (AsyncResult.Loading<T>) -> Unit): Flow<AsyncResult<T>> =
    onEach {
        if (it is AsyncResult.Loading.Progress) {
            block(it)
        }
    }