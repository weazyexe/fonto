package dev.weazyexe.fonto.ui.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.ui.screens.BottomBarNavGraph
import dev.weazyexe.fonto.ui.screens.feed.viewstates.PostsViewState

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(rootPaddingValues: PaddingValues) {
    FeedBody(
        postsViewState = PostsViewState(emptyList()),
        rootPaddingValues = rootPaddingValues
    )
}