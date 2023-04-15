package dev.weazyexe.fonto.ui.features.settings

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.debug.destinations.DebugScreenDestination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph

@BottomBarNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigateTo: (DirectionDestinationSpec) -> Unit
) {
    SettingsBody(
        onDebugClick = { navigateTo(DebugScreenDestination) }
    )
}