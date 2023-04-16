package dev.weazyexe.fonto.debug

import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State

data class DebugState(
    val stub: String = ""
) : State

sealed interface DebugEffect : Effect