package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

internal fun viewUpdate(
    message: FeedMessage.View,
    state: FeedState
): FeedUpdate = when (message) {
    is FeedMessage.View.OnLoadMore -> onLoadMorePostsUpdate(message, state)
    is FeedMessage.View.OnPostClick -> onPostClickUpdate(message, state)
    is FeedMessage.View.OnRefresh -> onRefreshUpdate(message, state)
    is FeedMessage.View.OnPostSaveClick -> onPostSaveClickUpdate(message, state)
    is FeedMessage.View.OnLoadPostMeta -> onLoadPostMetaUpdate(message, state)
    is FeedMessage.View.OnSearchBarActiveChange -> onSearchBarActiveChangeUpdate(message, state)
    is FeedMessage.View.OnManageFeedClick -> onManageFeedClickUpdate(message, state)
}
