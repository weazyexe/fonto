package dev.weazyexe.fonto.ui.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

private const val ANIMATION_DURATION_MS = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedAppearing(content: @Composable () -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        delay(100L)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(animationSpec = tween(ANIMATION_DURATION_MS)),
        exit = scaleOut(animationSpec = tween(ANIMATION_DURATION_MS))
    ) {
        content()
    }
}