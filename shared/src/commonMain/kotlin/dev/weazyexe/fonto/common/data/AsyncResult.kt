package dev.weazyexe.fonto.common.data

sealed interface AsyncResult<out T> {

    class Loading<out T> : AsyncResult<T>

    data class Success<T>(val data: T) : AsyncResult<T>

    data class Error<T>(val error: ResponseError) : AsyncResult<T>
}

fun <T, R> AsyncResult<T>.map(transform: (T) -> R): AsyncResult<R> =
    when (this) {
        is AsyncResult.Loading -> AsyncResult.Loading()
        is AsyncResult.Success -> AsyncResult.Success(transform(data))
        is AsyncResult.Error -> AsyncResult.Error(error)
    }