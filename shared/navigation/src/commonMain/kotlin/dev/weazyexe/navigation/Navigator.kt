package dev.weazyexe.navigation

import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.File
import kotlinx.coroutines.flow.Flow

interface Navigator {

    fun open(route: Route)

    fun <T> openForResult(route: Route, key: String): Flow<T>

    fun back()

    fun <T> backWithResult(result: T, key: String)

    fun openInAppBrowser(url: String, theme: Theme)

    fun openExternalBrowser(url: String)

    fun openFilePicker(mimeTypes: List<String>): Flow<File?>

    fun openFileCreator(fileName: String, mimeType: String): Flow<File?>
}
