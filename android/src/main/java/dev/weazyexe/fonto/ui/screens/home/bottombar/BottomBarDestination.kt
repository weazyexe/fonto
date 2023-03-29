package dev.weazyexe.fonto.ui.screens.home.bottombar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.screens.destinations.BookmarksScreenDestination
import dev.weazyexe.fonto.ui.screens.destinations.FeedScreenDestination
import dev.weazyexe.fonto.ui.screens.destinations.SettingsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Feed(FeedScreenDestination, Icons.Default.Feed, R.string.home_bottom_label_feed),
    Bookmarks(BookmarksScreenDestination, Icons.Default.Bookmark, R.string.home_bottom_label_bookmarks),
    Settings(SettingsScreenDestination, Icons.Default.Settings, R.string.home_bottom_label_settings),
}