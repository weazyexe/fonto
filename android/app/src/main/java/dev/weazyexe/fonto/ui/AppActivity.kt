package dev.weazyexe.fonto.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dev.weazyexe.fonto.BuildConfig
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.animation.SlideAnimations
import dev.weazyexe.fonto.core.ui.theme.FontoTheme
import dev.weazyexe.fonto.ui.navigation.AppNavGraph
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppActivity : ComponentActivity() {

    private val viewModel by viewModel<AppViewModel>()

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        if (BuildConfig.BUILD_TYPE != "benchmark") {
            splashScreen.setKeepOnScreenCondition {
                !viewModel.state.value.isInitialized
            }
        }

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val state by viewModel.state.collectAsState()

            FontoTheme(
                accentColor = state.accentColor.argb,
                darkTheme = when (state.theme) {
                    Theme.LIGHT -> false
                    Theme.DARK -> true
                    else -> isSystemInDarkTheme()
                },
                dynamicColor = state.isDynamicColorsEnabled
            ) {
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val homeNavController = rememberAnimatedNavController(bottomSheetNavigator)
                val animatedNavHostEngine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = { SlideAnimations.enter },
                        exitTransition = { SlideAnimations.exit },
                        popEnterTransition = { SlideAnimations.popEnter },
                        popExitTransition = { SlideAnimations.popExit }
                    )
                )

                Surface(
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                    }
                ) {
                    ModalBottomSheetLayout(
                        bottomSheetNavigator = bottomSheetNavigator,
                        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                    ) {
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
    }
}