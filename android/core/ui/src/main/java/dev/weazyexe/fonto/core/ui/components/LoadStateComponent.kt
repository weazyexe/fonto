package dev.weazyexe.fonto.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import dev.weazyexe.fonto.core.ui.presentation.LoadState

@Composable
fun <T> LoadStateComponent(
    loadState: LoadState<T>,
    onSuccess: @Composable (LoadState.Data<T>) -> Unit,
    onError: @Composable (LoadState.Error<T>) -> Unit,
    onLoading: @Composable (LoadState.Loading<T>) -> Unit,
    onSwipeRefresh: @Composable (T?) -> Unit = {}
) {
    Crossfade(loadState) {
        when (it) {
            is LoadState.Loading.SwipeRefresh -> onSwipeRefresh(it.data)
            is LoadState.Loading -> onLoading(it)
            is LoadState.Error -> onError(it)
            is LoadState.Data -> onSuccess(it)
        }
    }
}