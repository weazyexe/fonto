package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.arch.Effect
import dev.weazyexe.fonto.common.model.preference.Theme

sealed interface FeedEffect : Effect {

    data class OpenPostInApp(val link: String, val theme: Theme) : FeedEffect

    data class OpenPostInBrowser(val link: String) : FeedEffect

    data class ShowPostSavingErrorMessage(val isSaving: Boolean) : FeedEffect

    data class ShowPostSavedMessage(val isSaving: Boolean) : FeedEffect

    object ShowInvalidLinkMessage : FeedEffect

    object ScrollToTop : FeedEffect
}
