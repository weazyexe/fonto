package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<CategoriesViewModel>()

    CategoriesBody(
        onBackClick = navController::navigateUp
    )
}