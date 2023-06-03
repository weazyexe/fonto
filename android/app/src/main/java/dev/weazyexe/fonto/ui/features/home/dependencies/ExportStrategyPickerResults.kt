package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.ExportStrategyResults

interface ExportStrategyPickerResults {

    fun invoke(): ResultRecipient<ExportStrategyPickerDialogDestination, ExportStrategyResults?>
}