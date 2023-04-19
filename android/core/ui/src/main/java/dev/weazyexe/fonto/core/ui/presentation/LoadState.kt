package dev.weazyexe.fonto.core.ui.presentation

sealed interface LoadState<T> {

    sealed interface HasResult<T>

    sealed interface Loading<T> : LoadState<T> {

        class Default<T>() : Loading<T>

        data class SwipeRefresh<T>(val data: T?) : Loading<T>
    }

    data class Data<T>(val data: T) : LoadState<T>, HasResult<T>

    data class Error<T>(val error: ResponseError) : LoadState<T>, HasResult<T>
}

fun <T, VS> LoadState.Data<T>.asViewState(mapper: (T) -> VS): LoadState.Data<VS> {
    return LoadState.Data(mapper(data))
}