package dev.weazyexe.fonto.ui.features.home.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.destinations.BookmarksScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.SettingsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Feed(FeedScreenDestination, DrawableResources.ic_feed_24, StringResources.home_bottom_label_feed),
    Bookmarks(BookmarksScreenDestination, DrawableResources.ic_bookmark_24, StringResources.home_bottom_label_bookmarks),
    Settings(SettingsScreenDestination, DrawableResources.ic_settings_24, StringResources.home_bottom_label_settings),
}