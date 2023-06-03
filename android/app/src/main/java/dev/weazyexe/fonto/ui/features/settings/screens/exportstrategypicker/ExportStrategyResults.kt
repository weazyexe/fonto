package dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker

import android.os.Parcelable
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExportStrategyResults(
    val categories: Boolean,
    val feeds: Boolean,
    val posts: Boolean
) : Parcelable

fun ExportStrategyResults.toExportStrategy() =
    ExportStrategy(
        categories = categories,
        feeds = feeds,
        posts = posts
    )

fun ExportStrategy.toResults() =
    ExportStrategyResults(
        categories = categories,
        feeds = feeds,
        posts = posts
    )

