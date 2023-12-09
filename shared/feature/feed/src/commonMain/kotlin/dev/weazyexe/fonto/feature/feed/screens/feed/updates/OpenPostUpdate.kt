package dev.weazyexe.fonto.feature.feed.screens.feed.updates

import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedDependencies
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost.Difference
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

internal fun openPostUpdate(
    message: FeedMessage.OpenPost,
    state: FeedState,
    deps: FeedDependencies
): FeedUpdate {
    val link = message.post.link
    if (link == null || !deps.urlValidator.validate(link)) {
        return state with setOf(FeedEffects.Messenger.ShowInvalidLinkMessage)
    }

    val newPost = message.post.copy(isRead = true)
    val effects = when (message.openPostPreference) {
        OpenPostPreference.DEFAULT_BROWSER ->
            setOf(FeedEffects.Navigation.OpenLinkInBrowser(link))

        OpenPostPreference.INTERNAL ->
            setOf(
                FeedEffects.Navigation.OpenLinkInApp(
                    url = link,
                    theme = message.theme
                )
            )
    } + FeedEffects.UpdatePost(newPost, Difference.Read)

    return state with effects
}
