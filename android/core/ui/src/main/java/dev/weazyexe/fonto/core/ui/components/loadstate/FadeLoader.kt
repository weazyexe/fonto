package dev.weazyexe.fonto.core.ui.components.loadstate

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp

fun Modifier.fadeLoader(elevation: Dp) =
    composed {
        val transition = rememberInfiniteTransition()

        val alpha by transition.animateFloat(
            initialValue = 1f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(750),
                repeatMode = RepeatMode.Reverse
            )
        )

        background(
            brush = SolidColor(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation)),
            alpha = alpha
        )
    }