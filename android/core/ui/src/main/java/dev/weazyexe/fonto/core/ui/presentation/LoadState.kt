package dev.weazyexe.fonto.core.ui.presentation

import dev.weazyexe.fonto.common.data.ResponseError

sealed interface LoadState<T> {

    sealed interface HasResult<T>

    class Loading<T> : LoadState<T>

    data class Data<T>(val data: T) : LoadState<T>, HasResult<T>

    data class Error<T>(val error: ResponseError) : LoadState<T>, HasResult<T>
}

fun <T, VS> LoadState.Data<T>.asViewState(mapper: (T) -> VS): LoadState.Data<VS> {
    return LoadState.Data(mapper(data))
}
