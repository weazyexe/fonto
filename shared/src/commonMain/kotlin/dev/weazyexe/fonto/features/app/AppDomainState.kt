package dev.weazyexe.fonto.features.app

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme

data class AppDomainState(
    val theme: Theme = Theme.SYSTEM,
    val isDynamicColorsEnabled: Boolean = true,
    val accentColor: ColorScheme = ColorScheme.BLUE,
    val isInitialized: Boolean = false
) : DomainState
