package dev.weazyexe.fonto.debug

import androidx.annotation.StringRes
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State

data class DebugState(
    val stub: String = ""
) : State

sealed interface DebugEffect : Effect {

    data class ShowMessage(@StringRes val message: Int) : DebugEffect
}