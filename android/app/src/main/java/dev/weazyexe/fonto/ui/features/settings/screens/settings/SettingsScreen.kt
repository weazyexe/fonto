package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateWithResult
import dev.weazyexe.fonto.ui.features.settings.screens.valuepicker.ThemePickerArgs
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigateTo: NavigateTo,
    navigateWithResult: NavigateWithResult,
    themePickerResultRecipientProvider: () -> ResultRecipient<ThemePickerDialogDestination, Theme?>
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.uiState.collectAsState()

    themePickerResultRecipientProvider().onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                result.value?.let { viewModel.saveTheme(it) }
            }
        }
    }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is SettingsEffect.OpenManageFeedScreen -> navigateTo(ManageFeedScreenDestination())
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
        }
    }

    SettingsBody(
        settings = state.preferences,
        onTextPreferenceClick = viewModel::onTextPreferenceClick,
        onSwitchPreferenceClick = viewModel::onSwitchPreferenceClick,
        onCustomPreferenceClick = viewModel::onCustomPreferenceClick
    )
}