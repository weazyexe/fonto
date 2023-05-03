package dev.weazyexe.fonto.ui.features.feed.viewstates

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

@Immutable
data class FeedViewState(
    val id: Feed.Id,
    val title: String,
    val link: String,
    val icon: Bitmap?,
    val type: Feed.Type
)

fun Feed.asViewState() = FeedViewState(
    id = id,
    title = title,
    link = link,
    icon = icon?.asBitmap(),
    type = type
)

fun FeedViewState.asFeed() = Feed(
    id = id,
    title = title,
    link = link,
    icon = icon?.asLocalImage(),
    type = type
)
