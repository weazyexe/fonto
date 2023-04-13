package dev.weazyexe.fonto.ui.core.navigation.animation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@OptIn(ExperimentalAnimationApi::class)
object SlideAnimations {

    private const val ANIMATION_OFFSET_X = 100
    private const val ANIMATION_DURATION_MS = 400

    val enter = slideInHorizontally(
        initialOffsetX = { ANIMATION_OFFSET_X },
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))

    val exit = slideOutHorizontally(
        targetOffsetX = { -ANIMATION_OFFSET_X },
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))

    val popEnter = slideInHorizontally(
        initialOffsetX = { -ANIMATION_OFFSET_X },
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))


    val popExit = slideOutHorizontally(
        targetOffsetX = { ANIMATION_OFFSET_X },
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))

}