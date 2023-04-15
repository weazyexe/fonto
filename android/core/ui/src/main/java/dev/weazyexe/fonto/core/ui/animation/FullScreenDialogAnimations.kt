package dev.weazyexe.fonto.core.ui.animation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

object FullScreenDialogAnimations {

    private const val ANIMATION_DURATION_MS = 300

    val enter = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(ANIMATION_DURATION_MS)
    )

    val exit = slideOutVertically(
        targetOffsetY = { -it },
        animationSpec = tween(ANIMATION_DURATION_MS)
    )

    val popEnter = slideInVertically(
        initialOffsetY = { -it },
        animationSpec = tween(ANIMATION_DURATION_MS)
    )

    val popExit = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(ANIMATION_DURATION_MS)
    )
}