package dev.weazyexe.fonto.ui.features.home.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.features.destinations.BookmarksScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.SettingsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Feed(FeedScreenDestination, R.drawable.ic_feed_24, R.string.home_bottom_label_feed),
    Bookmarks(BookmarksScreenDestination, R.drawable.ic_bookmark_24, R.string.home_bottom_label_bookmarks),
    Settings(SettingsScreenDestination, R.drawable.ic_settings_24, R.string.home_bottom_label_settings),
}