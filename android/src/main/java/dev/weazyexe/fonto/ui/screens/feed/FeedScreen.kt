package dev.weazyexe.fonto.ui.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.ui.screens.BottomBarNavGraph
import dev.weazyexe.fonto.ui.screens.feed.components.PostViewStates
import dev.weazyexe.fonto.ui.screens.feed.viewstates.FeedViewState

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(rootPaddingValues: PaddingValues) {
    FeedBody(
        feedViewState = FeedViewState(
            posts = listOf(
                PostViewStates.default,
                PostViewStates.noPictures,
                PostViewStates.saved,
                PostViewStates.default,
                PostViewStates.noPictures,
                PostViewStates.saved,
                PostViewStates.default,
                PostViewStates.noPictures,
                PostViewStates.saved,
                PostViewStates.default,
                PostViewStates.noPictures,
                PostViewStates.saved,
                PostViewStates.default,
                PostViewStates.noPictures,
                PostViewStates.saved
            )
        ),
        rootPaddingValues = rootPaddingValues
    )
}