package dev.weazyexe.fonto.feature.feed.screens.feed

import android.content.Context
import dev.weazyexe.fonto.core.ui.utils.StringResources

actual class FeedUiStrings(context: Context) {

    actual val invalidLink: String =
        context.getString(StringResources.feed_invalid_link)

    actual val postSaved: String =
        context.getString(StringResources.feed_post_saved_to_bookmarks)
    actual val failedToSavePost: String =
        context.getString(StringResources.feed_post_saving_error)

    actual val postRemovedFromSaved: String =
        context.getString(StringResources.feed_post_removed_from_bookmarks)
    actual val failedToRemovePostFromSavedMessage: String =
        context.getString(StringResources.feed_post_removing_from_bookmarks_error)
}
