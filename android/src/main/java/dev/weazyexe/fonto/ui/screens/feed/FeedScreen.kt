package dev.weazyexe.fonto.ui.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.ui.screens.BottomBarNavGraph
import dev.weazyexe.fonto.ui.screens.feed.viewstates.FeedViewState
import dev.weazyexe.fonto.ui.screens.feed.viewstates.asViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(rootPaddingValues: PaddingValues) {
    val repository = FeedRepository()
    var feed: FeedViewState? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(feed) {
        feed = withContext(Dispatchers.IO) {
            repository.getFeed("https://vas3k.club/posts.rss")
        }.asViewState()
    }

    FeedBody(
        feedViewState = feed ?: FeedViewState(emptyList()),
        rootPaddingValues = rootPaddingValues
    )
}