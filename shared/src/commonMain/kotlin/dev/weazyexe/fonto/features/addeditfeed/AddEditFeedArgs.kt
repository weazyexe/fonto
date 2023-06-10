package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.arch.NavigationArguments
import dev.weazyexe.fonto.common.model.feed.Feed

data class AddEditFeedArgs(
    val id: Feed.Id? = null
) : NavigationArguments