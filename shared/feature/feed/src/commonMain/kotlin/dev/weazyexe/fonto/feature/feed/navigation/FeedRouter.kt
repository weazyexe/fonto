package dev.weazyexe.fonto.feature.feed.navigation

import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.navigation.Navigator
import dev.weazyexe.navigation.Route

class FeedRouter(private val navigator: Navigator) {

    fun openManageFeed() {
        navigator.open(Route.Feed.Manage)
    }

    fun openLinkInApp(url: String, theme: Theme) {
        navigator.openInAppBrowser(url, theme)
    }

    fun openLinkInBrowser(url: String) {
        navigator.openExternalBrowser(url)
    }
}
