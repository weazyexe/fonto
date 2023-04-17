package dev.weazyexe.fonto.core.ui.presentation

/**
 * Loading state for data
 *
 * @property data loaded data
 * @property error error what was thrown during the loading
 * @property isLoading loading state
 * @property isSwipeRefresh is loading was launched from swipe refresh
 * @property isTransparent is loading transparent
 */
class LoadState<T> private constructor(
    val data: T? = null,
    val error: ResponseError? = null,
    val isLoading: Boolean = false,
    val isSwipeRefresh: Boolean = false,
    val isTransparent: Boolean = false
) {

    fun hasError(): Boolean = error != null || data == null

    companion object {

        /**
         * Create empty [LoadState]
         */
        fun <T> initial() = LoadState<T>()

        /**
         * Create loading [LoadState]
         */
        fun <T> loading(
            isSwipeRefresh: Boolean = false,
            isTransparent: Boolean = false,
            oldData: T? = null
        ): LoadState<T> =
            LoadState(
                data = oldData,
                error = null,
                isLoading = true,
                isSwipeRefresh = isSwipeRefresh,
                isTransparent = isTransparent
            )

        /**
         * Create error [LoadState]
         */
        fun <T> error(
            e: ResponseError,
            oldData: T? = null
        ): LoadState<T> =
            LoadState(
                data = oldData,
                error = e,
                isLoading = false,
                isSwipeRefresh = false
            )

        /**
         * Create successful [LoadState] with data
         */
        fun <T> data(data: T): LoadState<T> =
            LoadState(
                data = data,
                error = null,
                isLoading = false,
                isSwipeRefresh = false
            )

        /**
         * Create successful [LoadState] with [Unit]
         */
        fun data(): LoadState<Unit> =
            LoadState(
                data = Unit,
                error = null,
                isLoading = false,
                isSwipeRefresh = false
            )
    }
}

fun <T, VS> LoadState<T>.asViewState(mapper: (T) -> VS): LoadState<VS> {
    val errorToThrow = LoadState.error<VS>(error ?: ResponseError.UnknownError())
    return LoadState.data(mapper(data ?: return errorToThrow))
}

fun <T, VS> NewLoadState.Data<T>.asViewState(mapper: (T) -> VS): NewLoadState.Data<VS> {
    return NewLoadState.Data(mapper(data))
}