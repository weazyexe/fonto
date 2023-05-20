package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.CategoriesScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.home.dependencies.ColorPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateWithResult
import dev.weazyexe.fonto.ui.features.home.dependencies.ThemePickerResults
import dev.weazyexe.fonto.ui.features.settings.screens.colorpicker.ColorPickerArgs
import dev.weazyexe.fonto.ui.features.settings.screens.themepicker.ThemePickerArgs
import io.github.aakira.napier.Napier

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    rootPaddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    navigateTo: NavigateTo,
    navigateWithResult: NavigateWithResult,
    themePickerResults: ThemePickerResults,
    colorPickerResults: ColorPickerResults
) {
    val state by viewModel.uiState.collectAsState()

    themePickerResults.invoke().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                result.value?.let { viewModel.saveTheme(it) }
            }
        }
    }

    colorPickerResults.invoke().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                viewModel.saveColor(result.value)
            }
        }
    }

    ReceiveEffect(viewModel.effects) {
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
                Napier.d { possibleValues.toString() }
                navigateWithResult(
                    ColorPickerDialogDestination(
                        args = ColorPickerArgs(
                            selectedColor = value,
                            colors = possibleValues
                        )
                    )
                )
            }
        }
    }

    SettingsBody(
        settings = state.preferences,
        hiddenPreferences = state.hiddenPreferences,
        rootPaddingValues = rootPaddingValues,
        onTextPreferenceClick = viewModel::onTextPreferenceClick,
        onSwitchPreferenceClick = viewModel::onSwitchPreferenceClick,
        onCustomPreferenceClick = viewModel::onCustomPreferenceClick
    )
}