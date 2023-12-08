package dev.weazyexe.fonto.feature.feed.screens.feed

import android.content.Context
import dev.weazyexe.fonto.core.ui.utils.StringResources

actual class FeedUiMessages(context: Context) {

    actual val invalidLinkMessage: String =
        context.getString(StringResources.feed_invalid_link)

    actual val postSavedMessage: String =
        context.getString(StringResources.feed_post_saved_to_bookmarks)
    actual val failedToSavePostMessage: String =
        context.getString(StringResources.feed_post_saving_error)

    actual val postRemovedFromSavedMessage: String =
        context.getString(StringResources.feed_post_removed_from_bookmarks)
    actual val failedToRemovePostFromSavedMessage: String =
        context.getString(StringResources.feed_post_removing_from_bookmarks_error)
}
