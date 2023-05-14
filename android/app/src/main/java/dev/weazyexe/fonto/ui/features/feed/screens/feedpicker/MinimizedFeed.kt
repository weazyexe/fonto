package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import android.os.Parcelable
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MinimizedFeed(
    val id: @RawValue Feed.Id,
    val title: String
) : Parcelable

fun Feed.minimize(): MinimizedFeed = MinimizedFeed(id, title)