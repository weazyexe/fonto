package dev.weazyexe.fonto.ui.screens.feed.managefeed

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.ui.core.presentation.State

data class ManageFeedState(
    val feedList: List<Feed>
): State