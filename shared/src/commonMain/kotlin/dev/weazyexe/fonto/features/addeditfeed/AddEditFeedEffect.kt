package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.arch.Effect

sealed interface AddEditFeedEffect : Effect {

    object ShowFeedLoadingFailureMessage : AddEditFeedEffect

    object ShowCategoriesLoadingFailureMessage : AddEditFeedEffect

    object ShowFaviconLoadingFailureMessage : AddEditFeedEffect

    object ShowTitleInvalidMessage : AddEditFeedEffect

    object ShowLinkInvalidMessage : AddEditFeedEffect

    object ShowFeedValidationError : AddEditFeedEffect

    object ShowFeedSavingError : AddEditFeedEffect

    data class NavigateUp(val isSuccessful: Boolean) : AddEditFeedEffect
}