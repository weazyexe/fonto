package dev.weazyexe.fonto.ui

import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State

data class AppState(
    val theme: Theme = Theme.SYSTEM,
    val isDynamicColorsEnabled: Boolean = true
) : State

sealed interface AppEffect : Effect