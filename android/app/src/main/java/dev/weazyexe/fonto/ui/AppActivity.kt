package dev.weazyexe.fonto.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dev.weazyexe.fonto.core.ui.animation.SlideAnimations
import dev.weazyexe.fonto.core.ui.theme.FontoTheme
import dev.weazyexe.fonto.ui.navigation.AppNavGraph

class AppActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FontoTheme {
                val homeNavController = rememberAnimatedNavController()
                val animatedNavHostEngine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = { SlideAnimations.enter },
                        exitTransition = { SlideAnimations.exit },
                        popEnterTransition = { SlideAnimations.popEnter },
                        popExitTransition = { SlideAnimations.popExit }
                    )
                )
                DestinationsNavHost(
                    navGraph = AppNavGraph,
                    modifier = Modifier.fillMaxSize(),
                    navController = homeNavController,
                    engine = animatedNavHostEngine
                )
            }
        }
    }
}