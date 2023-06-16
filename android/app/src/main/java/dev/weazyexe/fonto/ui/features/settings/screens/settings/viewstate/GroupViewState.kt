package dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.preference.Group

@Immutable
data class GroupViewState(
    val key: Group.Key,
    val title: String,
    val preferences: List<PreferenceViewState>
)