package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.features.settings.SettingsEffect
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.CategoriesScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.home.dependencies.ColorPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ExportStrategyPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.ThemePickerResults
import dev.weazyexe.fonto.ui.features.settings.screens.colorpicker.ColorPickerArgs
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.toExportStrategy
import dev.weazyexe.fonto.ui.features.settings.screens.themepicker.ThemePickerArgs
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    rootPaddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    navigateTo: NavigateTo,
    themePickerResults: ThemePickerResults,
    colorPickerResults: ColorPickerResults,
    exportStrategyResults: ExportStrategyPickerResults
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState(SettingsViewState())

    HandleNavigationResults(
        themePickerResults = themePickerResults,
        colorPickerResults = colorPickerResults,
        exportStrategyResults = exportStrategyResults,
        viewModel = viewModel
    )

    HandleEffects(
        effects = viewModel.effects,
        navigateTo = navigateTo,
        snackbarHostState = snackbarHostState,
        export = viewModel::export,
        import = viewModel::import
    )

    SettingsBody(
        settings = state.preferences,
        isLoading = state.isLoading,
        rootPaddingValues = rootPaddingValues,
        snackbarHostState = snackbarHostState,
        onPreferenceClick = viewModel::onPreferenceClick,
    )
}


@Composable
private fun HandleEffects(
    effects: Flow<SettingsEffect>,
    navigateTo: NavigateTo,
    snackbarHostState: SnackbarHostState,
    export: (uri: Uri) -> Unit,
    import: (uri: Uri) -> Unit,
) {
    val context = LocalContext.current
    val exportFontoSaver = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { it?.let { export(it) } }
    )

    val openDocumentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { it?.let { import(it) } }
    )

    ReceiveEffect(effects) {
        when (this) {
            is SettingsEffect.OpenManageFeedScreen -> navigateTo(ManageFeedScreenDestination())
            is SettingsEffect.OpenManageCategoriesScreen -> navigateTo(CategoriesScreenDestination())
            is SettingsEffect.OpenDebugScreen -> navigateTo(DebugScreenDestination())
            is SettingsEffect.OpenThemePicker -> {
                navigateTo(
                    ThemePickerDialogDestination(
                        args = ThemePickerArgs(
                            value = currentTheme,
                            possibleValues = Theme.values().toList(),
                            icon = DrawableResources.ic_lightbulb_24,
                            title = StringResources.settings_appearance_theme_title,
                        )
                    )
                )
            }

            is SettingsEffect.OpenColorSchemePicker -> {
                navigateTo(
                    ColorPickerDialogDestination(
                        args = ColorPickerArgs(
                            selectedColor = currentColorScheme,
                            colors = ColorScheme.values().toList()
                        )
                    )
                )
            }

            is SettingsEffect.OpenExportFilePicker -> {
                exportFontoSaver.launch(fileName)
            }

            is SettingsEffect.OpenExportStrategyPicker -> {
                navigateTo(ExportStrategyPickerDialogDestination())
            }

            is SettingsEffect.OpenImportFilePicker -> {
                openDocumentPicker.launch(arrayOf(fileMimeType))
            }

            is SettingsEffect.ShowExportFailureMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_export_fonto_file_saving_failed))
            }

            is SettingsEffect.ShowExportSuccessMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_export_fonto_successful))
            }

            is SettingsEffect.ShowImportFailureMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_import_fonto_data_import_failed))
            }

            is SettingsEffect.ShowImportSuccessMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_import_fonto_successful))
            }
        }
    }
}

@Composable
private fun HandleNavigationResults(
    themePickerResults: ThemePickerResults,
    colorPickerResults: ColorPickerResults,
    exportStrategyResults: ExportStrategyPickerResults,
    viewModel: SettingsViewModel
) {
    themePickerResults.invoke().handleResults { result ->
        result?.let { viewModel.onThemePicked(it) }
    }

    colorPickerResults.invoke().handleResults { result ->
        viewModel.onColorSchemePicked(result)
    }

    exportStrategyResults.invoke().handleResults { result ->
        result?.let { viewModel.chooseExportFileDestination(result.toExportStrategy()) }
    }
}
