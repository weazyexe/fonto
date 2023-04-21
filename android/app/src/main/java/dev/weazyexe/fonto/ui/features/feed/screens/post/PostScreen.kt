package dev.weazyexe.fonto.ui.features.feed.screens.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination(navArgsDelegate = PostScreenArgs::class)
@Composable
fun PostScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<PostViewModel>()
    val state by viewModel.uiState.collectAsState()

    PostBody(
        postLoadState = state.post,
        onBackClick = { navController.navigateUp() }
    )
}
