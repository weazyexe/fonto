package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.Effect
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.model.feed.Category

sealed interface SearchEffect : Effect {

    data class OpenFeedPicker(
        val values: List<ByFeed.Data>,
        val possibleValues: List<ByFeed.Data>
    ) : SearchEffect

    data class OpenCategoryPicker(
        val values: List<Category>,
        val possibleValues: List<Category>
    ) : SearchEffect
}