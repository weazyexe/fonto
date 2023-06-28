package dev.weazyexe.fonto.ui.features.feed.screens.categorypicker

import android.os.Parcelable
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CategoryPickerResults(
    val values: @RawValue List<Category>,
    val possibleValues: @RawValue List<Category>
) : Parcelable