package dev.weazyexe.fonto.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.util.customTabsColorScheme


object ExternalRouter {

    fun openInAppBrowser(
        context: Context,
        link: String,
        theme: Theme
    ) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setColorScheme(theme.customTabsColorScheme)
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

    fun openNotificationsSettings(context: Context) {
        val intent = Intent().apply {
            action = "android.settings.APP_NOTIFICATION_SETTINGS"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
        }

        context.startActivity(intent)
    }
}