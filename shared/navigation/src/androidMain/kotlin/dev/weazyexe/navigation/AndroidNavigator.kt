package dev.weazyexe.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.AndroidFile
import dev.weazyexe.fonto.core.File
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class AndroidNavigator(
    private val context: Context,
    private val navController: NavController,
    private val activityResultRegistry: ActivityResultRegistry,
    private val routeMapper: (Route) -> Direction
) : Navigator {

    override fun open(route: Route) {
        navController.navigate(routeMapper(route))

        Napier.i(
            tag = LOG_TAG,
            message = "Open $route"
        )
    }

    override fun <T> openForResult(route: Route, key: String): Flow<T> {
        navController.navigate(routeMapper(route))

        Napier.i(
            tag = LOG_TAG,
            message = "Open for result $route with key $key"
        )

        return navController.currentBackStackEntryFlow
            .map { entry -> entry.savedStateHandle }
            .mapNotNull { savedStateHandle -> savedStateHandle.get<T>(key) }
    }

    override fun <T> backWithResult(result: T, key: String) {
        navController.currentBackStackEntry?.savedStateHandle?.set(key, result)
        navController.navigateUp()

        Napier.i(
            tag = LOG_TAG,
            message = "Go back with result $result. Key $key"
        )
    }

    override fun back() {
        navController.navigateUp()

        Napier.i(
            tag = LOG_TAG,
            message = "Go back"
        )
    }

    override fun openInAppBrowser(url: String, theme: Theme) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setColorScheme(theme.customTabsColorScheme)
            .setShowTitle(true)
            .build()

        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabsIntent.launchUrl(context, Uri.parse(url))

        Napier.i(
            tag = LOG_TAG,
            message = "Open in-app browser: $url"
        )
    }

    override fun openExternalBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        Napier.i(
            tag = LOG_TAG,
            message = "Open external browser: $url"
        )
    }

    override fun openFilePicker(mimeTypes: List<String>): Flow<File?> = callbackFlow {
        val launcher = activityResultRegistry.register(
            FILE_PICKER_KEY,
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            launch { send(uri?.let(::AndroidFile)) }
            Napier.i(
                tag = LOG_TAG,
                message = "Send chosen file URI: $uri"
            )
            close()
        }

        launcher.launch(mimeTypes.toTypedArray())

        Napier.i(
            tag = LOG_TAG,
            message = "Open file picker: $mimeTypes"
        )

        awaitClose { launcher.unregister() }
    }

    override fun openFileCreator(
        fileName: String,
        mimeType: String
    ): Flow<File?> = callbackFlow {
        val launcher = activityResultRegistry.register(
            FILE_CREATOR_KEY,
            ActivityResultContracts.CreateDocument(mimeType)
        ) { uri ->
            launch { send(uri?.let(::AndroidFile)) }
            Napier.i(
                tag = LOG_TAG,
                message = "Send created file URI: $uri"
            )
            close()
        }

        launcher.launch(fileName)

        Napier.i(
            tag = LOG_TAG,
            message = "Open file creator: $fileName"
        )

        awaitClose { launcher.unregister() }
    }

    private val Theme.customTabsColorScheme : Int
        @StringRes
        get() = when (this) {
            Theme.LIGHT -> CustomTabsIntent.COLOR_SCHEME_LIGHT
            Theme.DARK -> CustomTabsIntent.COLOR_SCHEME_DARK
            Theme.SYSTEM -> CustomTabsIntent.COLOR_SCHEME_SYSTEM
        }

    private companion object {

        const val FILE_PICKER_KEY = "FILE_PICKER_KEY"
        const val FILE_CREATOR_KEY = "FILE_CREATOR_KEY"

        const val LOG_TAG = "AndroidNavigator"
    }

}
