package dev.weazyexe.fonto.ui.features.feed.components.feed

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

@Immutable
data class FeedViewState(
    val id: Feed.Id,
    val title: String,
    val link: String,
    val icon: Bitmap?,
    val type: Feed.Type,
    val category: Category
)

fun Feed.asViewState() = FeedViewState(
    id = id,
    title = title,
    link = link,
    icon = icon?.asBitmap(),
    type = type,
    category = category
)

fun FeedViewState.asFeed() = Feed(
    id = id,
    title = title,
    link = link,
    icon = icon?.asLocalImage(),
    type = type,
    category = category
)
