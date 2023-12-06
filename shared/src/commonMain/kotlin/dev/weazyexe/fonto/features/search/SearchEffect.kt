package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.Effect
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.preference.Theme

sealed interface SearchEffect : Effect {

    data class OpenFeedPicker(
        val values: List<ByFeed.Data>,
        val possibleValues: List<ByFeed.Data>
    ) : SearchEffect

    data class OpenCategoryPicker(
        val values: List<Category>,
        val possibleValues: List<Category>
    ) : SearchEffect

    data class OpenPostInApp(val link: String, val theme: Theme) : SearchEffect

    data class OpenPostInBrowser(val link: String) : SearchEffect

    data class ShowPostSavingErrorMessage(val isSaving: Boolean) : SearchEffect

    data class ShowPostSavedMessage(val isSaving: Boolean) : SearchEffect

    object ShowInvalidLinkMessage : SearchEffect
}
