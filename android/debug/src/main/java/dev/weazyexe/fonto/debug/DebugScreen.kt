package dev.weazyexe.fonto.debug

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun DebugScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<DebugViewModel>()


    DebugBody(
        onBackClick = { navController.navigateUp() },
        onMockFeedClick = viewModel::addMockFeeds
    )
}