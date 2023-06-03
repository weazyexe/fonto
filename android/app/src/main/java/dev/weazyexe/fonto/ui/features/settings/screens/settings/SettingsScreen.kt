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
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.CategoriesScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.home.dependencies.ColorPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ExportStrategyPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateWithResult
import dev.weazyexe.fonto.ui.features.home.dependencies.ThemePickerResults
import dev.weazyexe.fonto.ui.features.settings.screens.colorpicker.ColorPickerArgs
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.toExportStrategy
import dev.weazyexe.fonto.ui.features.settings.screens.themepicker.ThemePickerArgs
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    rootPaddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    navigateTo: NavigateTo,
    navigateWithResult: NavigateWithResult,
    themePickerResults: ThemePickerResults,
    colorPickerResults: ColorPickerResults,
    exportStrategyResults: ExportStrategyPickerResults
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.uiState.collectAsState()

    themePickerResults.invoke().handleResults { result ->
        result?.let { viewModel.saveTheme(it) }
    }

    colorPickerResults.invoke().handleResults { result ->
        viewModel.saveColor(result)
    }

    exportStrategyResults.invoke().handleResults { result ->
        result?.let { viewModel.startExporting(result.toExportStrategy()) }
    }

    HandleEffects(
        effects = viewModel.effects,
        navigateTo = navigateTo,
        navigateWithResult = navigateWithResult,
        snackbarHostState = snackbarHostState,
        saveFile = viewModel::saveFile
    )

    SettingsBody(
        settings = state.preferences,
        isLoading = state.isLoading,
        hiddenPreferences = state.hiddenPreferences,
        rootPaddingValues = rootPaddingValues,
        snackbarHostState = snackbarHostState,
        onTextPreferenceClick = viewModel::onTextPreferenceClick,
        onSwitchPreferenceClick = viewModel::onSwitchPreferenceClick,
        onCustomPreferenceClick = viewModel::onCustomPreferenceClick
    )
}

@Composable
private fun HandleEffects(
    effects: Flow<SettingsEffect>,
    navigateTo: NavigateTo,
    navigateWithResult: NavigateWithResult,
    snackbarHostState: SnackbarHostState,
    saveFile: (uri: Uri) -> Unit
) {
    val context = LocalContext.current
    val exportFontoSaver = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { it?.let { saveFile(it) } }
    )

    ReceiveEffect(effects) {
        when (this) {
            is SettingsEffect.OpenManageFeedScreen -> navigateTo(ManageFeedScreenDestination())
            is SettingsEffect.OpenCategoriesScreen -> navigateTo(CategoriesScreenDestination())
            is SettingsEffect.OpenDebugScreen -> navigateTo(DebugScreenDestination())
            is SettingsEffect.OpenThemePickerDialog -> {
                navigateWithResult(
                    ThemePickerDialogDestination(
                        args = ThemePickerArgs(
                            value = value,
                            possibleValues = possibleValues,
                            icon = icon,
                            title = title
                        )
                    )
                )
            }

            is SettingsEffect.OpenColorPickerDialog -> {
                navigateWithResult(
                    ColorPickerDialogDestination(
                        args = ColorPickerArgs(
                            selectedColor = value,
                            colors = possibleValues
                        )
                    )
                )
            }

            is SettingsEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(context.getString(message))
            }

            is SettingsEffect.ExportFonto -> {
                exportFontoSaver.launch(
                    context.getString(
                        StringResources.settings_export_fonto_default_file_name,
                        Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .toString()
                    )
                )
            }

            is SettingsEffect.OpenExportStrategyPicker -> {
                navigateTo(ExportStrategyPickerDialogDestination())
            }
        }
    }
}