package dev.weazyexe.fonto.ui.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.domain.OpenPostPreference
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

    SettingsBody(
        openPostPreferenceValue = state.openPostPreference == OpenPostPreference.INTERNAL,
        onDebugClick = { navigateTo(DebugScreenDestination) },
        onManageFeedClick = { navigateTo(ManageFeedScreenDestination) },
        onOpenPostPreferenceCheck = viewModel::onOpenPostPreferenceChange
    )
}