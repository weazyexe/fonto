package dev.weazyexe.fonto.ui.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigateTo: (DirectionDestinationSpec) -> Unit
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.uiState.collectAsState()

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is SettingsEffect.OpenManageFeedScreen -> navigateTo(ManageFeedScreenDestination)
            is SettingsEffect.OpenDebugScreen -> navigateTo(DebugScreenDestination)
        }
    }

    SettingsBody(
        settings = state.preferences,
        onTextPreferenceClick = viewModel::onTextPreferenceClick,
        onSwitchPreferenceClick = viewModel::onSwitchPreferenceClick
    )
}