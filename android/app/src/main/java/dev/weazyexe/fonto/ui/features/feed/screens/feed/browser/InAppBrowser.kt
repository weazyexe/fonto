package dev.weazyexe.fonto.ui.features.feed.screens.feed.browser

import android.content.Context
import android.net.Uri
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.util.customTabsColorScheme

object InAppBrowser {

    fun openPost(
        context: Context,
        link: String,
        theme: Theme
    ) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setColorScheme(theme.customTabsColorScheme)
            .setCloseButtonIcon(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_arrow_back_24
                )!!.toBitmap()
            )
            .setShowTitle(true)
            .build()
        customTabsIntent.launchUrl(context, Uri.parse(link))
    }
}