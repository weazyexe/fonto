package dev.weazyexe.fonto.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import dev.weazyexe.fonto.core.ui.presentation.LoadState

@Composable
fun <T> LoadStateComponent(
    loadState: LoadState<T>,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (LoadState.Error<T>) -> Unit,
    onLoading: @Composable () -> Unit
) {
    Crossfade(loadState::class, label = "load_state") { clazz ->
        when (clazz) {
            LoadState.Loading::class -> {
                (loadState as? LoadState.Loading)?.also { onLoading() }
            }

            LoadState.Error::class -> {
                (loadState as? LoadState.Error)?.also { onError(it) }
            }

            LoadState.Data::class -> {
                val data = (loadState as? LoadState.Data)?.data ?: return@Crossfade
                onSuccess(data)
            }
        }
    }
}
