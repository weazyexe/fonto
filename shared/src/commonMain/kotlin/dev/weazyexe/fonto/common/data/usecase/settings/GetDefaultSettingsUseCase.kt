package dev.weazyexe.fonto.common.data.usecase.settings

import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Group
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.Theme

internal class GetDefaultSettingsUseCase {

    operator fun invoke(): List<Group> = listOf(
        Group(
            key = Group.Key.FEED,
            preferences = listOf(
                Preference.Text(Preference.Key.MANAGE_FEED),
                Preference.Text(Preference.Key.MANAGE_CATEGORIES),
                Preference.Switch(
                    key = Preference.Key.OPEN_POST,
                    value = true
                ),
            )
        ),
        Group(
            key = Group.Key.APPEARANCE,
            preferences = listOf(
                Preference.Value(
                    key = Preference.Key.THEME,
                    value = Theme.SYSTEM,
                    possibleValues = Theme.values().toList()
                ),
                Preference.Switch(
                    key = Preference.Key.DYNAMIC_COLORS,
                    value = false
                ),
                Preference.Value(
                    key = Preference.Key.COLOR_SCHEME,
                    value = ColorScheme.BLUE,
                    possibleValues = ColorScheme.values().toList()
                ),
            )
        ),
        Group(
            key = Group.Key.BACKUP,
            preferences = listOf(
                Preference.Text(Preference.Key.EXPORT_FONTO),
                Preference.Text(Preference.Key.IMPORT_FONTO),
            )
        ),
        Group(
            key = Group.Key.DEBUG,
            preferences = listOf(
                Preference.Text(Preference.Key.DEBUG_MENU),
            )
        )
    )
}