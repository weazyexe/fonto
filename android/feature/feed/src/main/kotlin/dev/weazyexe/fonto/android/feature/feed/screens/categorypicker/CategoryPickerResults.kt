package dev.weazyexe.fonto.android.feature.feed.screens.categorypicker

import android.os.Parcelable
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.android.feature.feed.screens.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CategoryPickerResults(
    val values: @RawValue List<Category>,
    val possibleValues: @RawValue List<Category>
) : Parcelable

typealias CategoryPickerResult = ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResults?>
