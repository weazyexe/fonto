package dev.weazyexe.fonto.ui.features.feed.viewstates

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.model.feed.Feed

@Immutable
data class FeedViewState(
    val id: Long,
    val title: String,
    val link: String,
    val icon: Bitmap?
)

fun Feed.asViewState() = FeedViewState(
    id = id,
    title = title,
    link = link,
    icon = icon?.asBitmap()
)