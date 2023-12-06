package dev.weazyexe.fonto.features.settings

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.feature.backup.FileReader
import dev.weazyexe.fonto.common.feature.backup.FileSaver
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.Theme

abstract class SettingsPresentation : Presentation<SettingsDomainState, SettingsEffect>() {

    abstract fun onPreferenceClick(preference: Preference)

    abstract fun onThemePicked(theme: Theme)

    abstract fun onColorSchemePicked(colorScheme: ColorScheme)

    abstract fun chooseExportFileDestination(strategy: ExportStrategy)

    abstract fun export(saver: FileSaver)

    abstract fun import(reader: FileReader)
}
