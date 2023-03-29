package dev.weazyexe.fonto.ui.screens.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.ui.screens.BottomBarNavGraph

@BottomBarNavGraph
@Destination
@Composable
fun BookmarksScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Bookmarks")
    }
}