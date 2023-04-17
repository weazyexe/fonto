package dev.weazyexe.fonto.core.ui.presentation

sealed interface NewLoadState<T> {

    sealed interface HasResult<T>

    data class Loading<T>(val isSwipeRefresh: Boolean = false) : NewLoadState<T>

    data class Data<T>(val data: T) : NewLoadState<T>, HasResult<T>

    data class Error<T>(val error: ResponseError) : NewLoadState<T>, HasResult<T>
}