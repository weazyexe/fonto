package dev.weazyexe.fonto.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.animation.SlideAnimations
import dev.weazyexe.fonto.core.ui.theme.FontoTheme
import dev.weazyexe.fonto.ui.navigation.AppNavGraph
import dev.weazyexe.messenger.Messenger
import dev.weazyexe.navigation.Navigator
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AppActivity : ScopeActivity() {

    private val viewModel by viewModel<AppViewModel>()

    @OptIn(
        ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            !viewModel.state.value.isInitialized
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
                val snackbarHostState = remember { SnackbarHostState() }
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val homeNavController = rememberNavController(bottomSheetNavigator)
                val animatedNavHostEngine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = { SlideAnimations.enter },
                        exitTransition = { SlideAnimations.exit },
                        popEnterTransition = { SlideAnimations.popEnter },
                        popExitTransition = { SlideAnimations.popExit }
                    )
                )

                /*val navigator = remember {
                    AndroidNavigator(
                        context = applicationContext,
                        navController = homeNavController,
                        activityResultRegistry = activityResultRegistry,
                        routeMapper = { it.asDirection() }
                    )
                }
                val messenger = remember {
                    AndroidMessenger(snackbarHostState)
                }*/
                scope.get<Navigator> { parametersOf(homeNavController, activityResultRegistry) }
                scope.get<Messenger> { parametersOf(snackbarHostState) }

                Surface(
                    modifier = Modifier.semantics { testTagsAsResourceId = true }
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
                            engine = animatedNavHostEngine,
                            dependenciesContainerBuilder = {
                                dependency(snackbarHostState)
                            }
                        )
                    }
                }
            }
        }
    }
}
