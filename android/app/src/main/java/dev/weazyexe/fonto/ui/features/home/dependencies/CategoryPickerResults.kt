package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.categorypicker.CategoryPickerResult

interface CategoryPickerResults {

    fun invoke(): ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResult?>
}