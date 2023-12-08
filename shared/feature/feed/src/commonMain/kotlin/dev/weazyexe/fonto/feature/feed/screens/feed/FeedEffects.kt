package dev.weazyexe.fonto.feature.feed.screens.feed

import dev.weazyexe.elm.effects.Effect
import dev.weazyexe.elm.effects.MessageEffect
import dev.weazyexe.elm.effects.adaptIdle
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.preference.Theme
import kotlinx.coroutines.flow.map

internal object FeedEffects {

    sealed interface Messenger {

        data object ShowInvalidLinkMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiMessages.invalidLinkMessage })

        data object ShowPostSavedMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiMessages.postSavedMessage })

        data object ShowPostSavingErrorMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiMessages.failedToSavePostMessage })

        data object ShowPostRemovedFromSavedMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiMessages.postRemovedFromSavedMessage })

        data object ShowPostRemovingFromSavedErrorMessage : FeedEffect by MessageEffect()
            .adaptIdle({ it.messenger to it.uiMessages.failedToRemovePostFromSavedMessage })
    }

    sealed interface Navigation {

        data object OpenManageFeed :
            FeedEffect by Effect.idle({ deps ->
                deps.navigator.openManageFeed()
            })

        data class OpenLinkInApp(val url: String, val theme: Theme) :
            FeedEffect by Effect.idle({ deps ->
                deps.navigator.openLinkInApp(url, theme)
            })

        data class OpenLinkInBrowser(val url: String) :
            FeedEffect by Effect.idle({ deps ->
                deps.navigator.openLinkInBrowser(url)
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
                .map { FeedMessage.Request.GettingPosts(it) }
        })

    data class UpdatePost(val post: Post) :
        FeedEffect by Effect.flow({ deps ->
            deps.updatePost(post)
                .map { FeedMessage.Request.UpdatingPost(it) }
        })

    data class GetUrlOpenPreferences(val post: Post) :
        FeedEffect by Effect.single({ deps ->
            val theme = deps.settingsStorage.getTheme()
            val openPostPreference = deps.settingsStorage.getOpenPostPreference()
            FeedMessage.OpenPost(post, theme, openPostPreference)
        })
}
