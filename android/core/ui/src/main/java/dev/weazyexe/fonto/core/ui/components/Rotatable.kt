package dev.weazyexe.fonto.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun Rotatable(
    isRotated: Boolean,
    content: @Composable () -> Unit
) {
    val rotation = if (isRotated) {
        -180f
    } else {
        0f
    }
    val angle: Float by animateFloatAsState(
        targetValue = if (rotation > 360 - rotation) {
            -(360 - rotation)
        } else {
            rotation
        },
        animationSpec = tween(
            durationMillis = 150,
            easing = LinearEasing
        ),
        label = ""
    )

    Box(modifier = Modifier.rotate(angle)) {
        content()
    }
}