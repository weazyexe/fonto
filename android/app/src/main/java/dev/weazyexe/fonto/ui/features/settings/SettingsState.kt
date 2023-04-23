package dev.weazyexe.fonto.ui.features.settings

import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.domain.OpenPostPreference

data class SettingsState(
    val openPostPreference: OpenPostPreference = OpenPostPreference.INTERNAL
) : State

sealed interface SettingsEffect : Effect