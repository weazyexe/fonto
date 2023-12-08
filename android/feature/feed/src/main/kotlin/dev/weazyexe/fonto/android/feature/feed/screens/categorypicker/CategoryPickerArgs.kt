package dev.weazyexe.fonto.android.feature.feed.screens.categorypicker

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryPickerArgs(
    val values: List<Category>,
    val possibleValues: List<Category>,
    @StringRes val title: Int
)
