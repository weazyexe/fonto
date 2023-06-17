package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.common.feature.backup.AndroidFileReader
import dev.weazyexe.fonto.common.feature.backup.AndroidFileSaver
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.features.settings.SettingsPresentation
import dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper.asDomainState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper.asViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.PreferenceViewState
import kotlinx.coroutines.flow.map

class SettingsViewModel(
    private val presentation: SettingsPresentation,
    private val context: App
) : ViewModel() {

    val state = presentation.domainState.map { it.asViewState(context) }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun onPreferenceClick(preferenceViewState: PreferenceViewState) {
        val preference = preferenceViewState.asDomainState()
        presentation.onPreferenceClick(preference)
    }

    fun onThemePicked(theme: Theme) {
        presentation.onThemePicked(theme)
    }

    fun onColorSchemePicked(colorScheme: ColorScheme) {
        presentation.onColorSchemePicked(colorScheme)
    }

    fun chooseExportFileDestination(strategy: ExportStrategy) {
        presentation.chooseExportFileDestination(strategy)
    }

    fun export(uri: Uri) {
        val saver = AndroidFileSaver(context, uri)
        presentation.export(saver)
    }

    fun import(uri: Uri) {
        val reader = AndroidFileReader(context, uri)
        presentation.import(reader)
    }
}