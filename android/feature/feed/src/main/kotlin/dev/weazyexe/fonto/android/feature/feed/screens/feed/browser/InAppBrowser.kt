package dev.weazyexe.fonto.android.feature.feed.screens.feed.browser

import android.content.Context
import android.net.Uri
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

object InAppBrowser {

    fun openPost(
        context: Context,
        link: String,
        theme: Theme
    ) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setCloseButtonIcon(
                AppCompatResources.getDrawable(
                    context,
                    DrawableResources.ic_arrow_back_24
                )!!.toBitmap()
            )
            .setShowTitle(true)
            .build()
        customTabsIntent.launchUrl(context, Uri.parse(link))
    }
}
