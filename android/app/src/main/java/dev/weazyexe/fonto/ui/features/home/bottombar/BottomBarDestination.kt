package dev.weazyexe.fonto.ui.features.home.bottombar

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.parcelize.Parcelize

@Parcelize
enum class BottomBarDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) : Parcelable {
    Feed(DrawableResources.ic_feed_24, StringResources.home_bottom_label_feed),
    Bookmarks(DrawableResources.ic_bookmark_24, StringResources.home_bottom_label_bookmarks),
    Settings(DrawableResources.ic_settings_24, StringResources.home_bottom_label_settings),
}
