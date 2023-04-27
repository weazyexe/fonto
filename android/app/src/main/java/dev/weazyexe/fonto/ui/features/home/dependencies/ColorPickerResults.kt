package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination

interface ColorPickerResults {

    fun invoke(): ResultRecipient<ColorPickerDialogDestination, Long>
}