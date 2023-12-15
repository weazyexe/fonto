package dev.weazyexe.fonto.feature.feed.screens.feed

import dev.weazyexe.elm.effects.Effect
import dev.weazyexe.elm.effects.MessageEffect
import dev.weazyexe.elm.effects.adaptIdle
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.OpenPost
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost.Difference
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

internal object FeedEffects {

    sealed interface Messenger {

        data object ShowInvalidLinkMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiStrings.invalidLink })

        data object ShowPostSavedMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiStrings.postSaved })

        data object ShowPostSavingErrorMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiStrings.failedToSavePost })

        data object ShowPostRemovedFromSavedMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiStrings.postRemovedFromSaved })

        data object ShowPostRemovingFromSavedErrorMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiStrings.failedToRemovePostFromSavedMessage })
    }

    sealed interface Navigation {

        data object OpenManageFeed :
            FeedEffect by Effect.onMain.idle({ deps ->
                deps.router.openManageFeed()
            })

        data class OpenLinkInApp(val url: String, val theme: Theme) :
            FeedEffect by Effect.onMain.idle({ deps ->
                deps.router.openLinkInApp(url, theme)
            })

        data class OpenLinkInBrowser(val url: String) :
            FeedEffect by Effect.onMain.idle({ deps ->
                deps.router.openLinkInBrowser(url)
            })
    }

    data class GetPosts(
        val limit: Int,
        val offset: Int,
        val useCache: Boolean,
        val shouldShowLoading: Boolean = true
    ) :
        FeedEffect by Effect.flow({ deps ->
            deps.getPosts(limit, offset, useCache, shouldShowLoading)
                .map { Request.GettingPosts(it) }
        })

    data class UpdatePost(
        val post: Post,
        val difference: Difference
    ) :
        FeedEffect by Effect.flow({ deps ->
            deps.updatePost(post)
                .map { UpdatingPost(it, difference) }
        })

    data class GetUrlOpenPreferences(val post: Post) :
        FeedEffect by Effect.single({ deps ->
            val theme = deps.settingsStorage.getTheme()
            val openPostPreference = deps.settingsStorage.getOpenPostPreference()
            OpenPost(post, theme, openPostPreference)
        })

    data class GetPostMetadata(val post: Post) :
        FeedEffect by Effect.flow({ deps ->
            val link = post.link ?: return@flow emptyFlow()
            deps.getPostMetadataFromHtml(link)
                .map { Request.GettingPostMeta(post, it) }
        })
}
