package dev.weazyexe.fonto.ui.features.settings

import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.domain.OpenPostPreference
import dev.weazyexe.fonto.ui.features.settings.model.Group
import dev.weazyexe.fonto.ui.features.settings.model.Preference

data class SettingsState(
    val preferences: List<Group> = PREFERENCES,
    val openPostPreference: OpenPostPreference = OpenPostPreference.INTERNAL
) : State

sealed interface SettingsEffect : Effect {

    object OpenManageFeedScreen : SettingsEffect

    object OpenDebugScreen : SettingsEffect
}

private val PREFERENCES = listOf(
    Group(
        title = R.string.settings_feed_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.MANAGE_FEED,
                title = R.string.settings_feed_manage_title,
                subtitle = R.string.settings_feed_manage_description,
                icon = R.drawable.ic_feed_24
            ),
            Preference.Switch(
                id = Preference.Identifier.OPEN_POST,
                title = R.string.settings_feed_open_post_title,
                subtitle = R.string.settings_feed_open_post_description,
                icon = R.drawable.ic_language_24,
                value = true
            )
        )
    ),
    Group(
        title = R.string.settings_debug_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.DEBUG_MENU,
                title = R.string.settings_debug_menu_title,
                subtitle = R.string.settings_debug_menu_description,
                icon = R.drawable.ic_bug_24
            )
        )
    )
)