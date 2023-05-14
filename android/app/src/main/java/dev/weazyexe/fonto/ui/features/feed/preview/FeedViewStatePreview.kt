package dev.weazyexe.fonto.ui.features.feed.preview

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

object FeedViewStatePreview {

    val default: FeedViewState
        @Composable
        get() {
            val context = LocalContext.current
            val icon = AppCompatResources.getDrawable(context, R.drawable.preview_favicon)?.toBitmap()
            return FeedViewState(
                id = Feed.Id(0L),
                title = "Rozetked",
                link = "https://rozetked.me/turbo",
                icon = icon,
                type = Feed.Type.RSS
            )
        }

    val noIcon: FeedViewState
        @Composable
        get() =
            FeedViewState(
                id = Feed.Id(1L),
                title = "DTF",
                link = "https://dtf.ru/rss/all",
                icon = null,
                type = Feed.Type.RSS
            )

}
