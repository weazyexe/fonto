package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination

interface ThemePickerResults {

    fun invoke(): ResultRecipient<ThemePickerDialogDestination, Theme?>
}