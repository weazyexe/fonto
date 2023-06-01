package dev.weazyexe.fonto.ui.features.feed.components.panes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams

@Composable
fun NotFoundPane(modifier: Modifier = Modifier) {
    ErrorPane(
        params = ErrorPaneParams.notFound(),
        modifier = modifier
    )
}