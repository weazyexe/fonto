package dev.weazyexe.fonto.ui.features.settings.screens.settings

import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.GroupsViewState

data class SettingsViewState(
    val preferences: GroupsViewState = GroupsViewState(emptyList()),
    val isLoading: Boolean = false
)
