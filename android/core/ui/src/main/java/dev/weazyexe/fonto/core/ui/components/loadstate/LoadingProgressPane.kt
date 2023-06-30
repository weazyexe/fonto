package dev.weazyexe.fonto.core.ui.components.loadstate

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Composable
fun LoadingProgressPane(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedLoadingIndicator(
            indicatorProgress = current.toFloat() / total,
        )

        Text(text = stringResource(id = StringResources.feed_synchronization),)
    }
}

@Composable
private fun AnimatedLoadingIndicator(indicatorProgress: Float) {
    var progress by remember { mutableStateOf(0f) }
    val progressAnimDuration = 300
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
    )
    LinearProgressIndicator(
        progress = progressAnimation
    )

    LaunchedEffect(indicatorProgress) {
        progress = indicatorProgress
    }
}