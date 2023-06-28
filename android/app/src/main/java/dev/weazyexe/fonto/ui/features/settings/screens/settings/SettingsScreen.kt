package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.features.settings.SettingsEffect
import dev.weazyexe.fonto.ui.features.destinations.CategoriesScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.home.ColorPickerResult
import dev.weazyexe.fonto.ui.features.home.ExportStrategyPickerResult
import dev.weazyexe.fonto.ui.features.home.ThemePickerResult
import dev.weazyexe.fonto.ui.features.settings.screens.colorpicker.ColorPickerArgs
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.toExportStrategy
import dev.weazyexe.fonto.ui.features.settings.screens.themepicker.ThemePickerArgs
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreen(
    rootPaddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    navController: NavController,
    themePickerResult: ThemePickerResult,
    colorPickerResult: ColorPickerResult,
    exportStrategyPickerResults: ExportStrategyPickerResult,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState(SettingsViewState())

    BackHandler { onBack() }

    HandleNavigationResults(
        themePickerResults = themePickerResult,
        colorPickerResults = colorPickerResult,
        exportStrategyPickerResults = exportStrategyPickerResults,
        viewModel = viewModel
    )

    HandleEffects(
        effects = viewModel.effects,
        navController = navController,
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
    navController: NavController,
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
            is SettingsEffect.OpenManageFeedScreen ->
                navController.navigate(ManageFeedScreenDestination())

            is SettingsEffect.OpenManageCategoriesScreen ->
                navController.navigate(CategoriesScreenDestination())

            is SettingsEffect.OpenDebugScreen ->
                navController.navigate(DebugScreenDestination())

            is SettingsEffect.OpenThemePicker ->
                navController.navigate(
                    ThemePickerDialogDestination(
                        args = ThemePickerArgs(
                            value = currentTheme,
                            possibleValues = Theme.values().toList(),
                            icon = DrawableResources.ic_lightbulb_24,
                            title = StringResources.settings_appearance_theme_title,
                        )
                    )
                )

            is SettingsEffect.OpenColorSchemePicker ->
                navController.navigate(
                    ColorPickerDialogDestination(
                        args = ColorPickerArgs(
                            selectedColor = currentColorScheme,
                            colors = ColorScheme.values().toList()
                        )
                    )
                )

            is SettingsEffect.OpenExportFilePicker ->
                exportFontoSaver.launch(fileName)

            is SettingsEffect.OpenExportStrategyPicker ->
                navController.navigate(ExportStrategyPickerDialogDestination())

            is SettingsEffect.OpenImportFilePicker ->
                openDocumentPicker.launch(arrayOf(fileMimeType))

            is SettingsEffect.ShowExportFailureMessage ->
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_export_fonto_file_saving_failed))

            is SettingsEffect.ShowExportSuccessMessage ->
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_export_fonto_successful))

            is SettingsEffect.ShowImportFailureMessage ->
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_import_fonto_data_import_failed))

            is SettingsEffect.ShowImportSuccessMessage ->
                snackbarHostState.showSnackbar(context.getString(StringResources.settings_import_fonto_successful))
        }
    }
}

@Composable
private fun HandleNavigationResults(
    themePickerResults: ThemePickerResult,
    colorPickerResults: ColorPickerResult,
    exportStrategyPickerResults: ExportStrategyPickerResult,
    viewModel: SettingsViewModel
) {
    themePickerResults.handleResults { result ->
        result?.let { viewModel.onThemePicked(it) }
    }

    colorPickerResults.handleResults { result ->
        viewModel.onColorSchemePicked(result)
    }

    exportStrategyPickerResults.handleResults { result ->
        result?.let { viewModel.chooseExportFileDestination(result.toExportStrategy()) }
    }
}
