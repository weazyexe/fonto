package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import dev.weazyexe.fonto.common.model.feed.Feed

data class AddEditFeedScreenArgs(
    val feedId: Feed.Id? = null,
    val feedTitle: String = "",
    val feedLink: String = ""
)
