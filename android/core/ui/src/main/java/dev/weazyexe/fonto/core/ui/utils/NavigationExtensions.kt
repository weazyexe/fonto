package dev.weazyexe.fonto.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationSpec

@Stable
@Composable
fun <D : DestinationSpec<*>, R> ResultRecipient<D, R>.handleResults(block: (R) -> Unit) {
    this.onNavResult {
        when (it) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                block(it.value)
            }
        }
    }
}
