package dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.core.ui.components.preferences.AlertDialogPicker
import dev.weazyexe.fonto.core.ui.components.preferences.CheckboxWithText
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun ExportStrategyPickerDialog(
    resultBackNavigator: ResultBackNavigator<ExportStrategyResults?>
) {
    val scrollState = rememberScrollState()

    var areCategoriesEnabled by remember { mutableStateOf(true) }
    var areFeedsEnabled by remember { mutableStateOf(true) }
    var arePostsEnabled by remember { mutableStateOf(true) }

    val exportStrategy = remember(areCategoriesEnabled, areFeedsEnabled, arePostsEnabled) {
        val updatedFeedsEnabled = areFeedsEnabled && areCategoriesEnabled
        val updatedPostsEnabled = arePostsEnabled && updatedFeedsEnabled

        ExportStrategy(
            categories = areCategoriesEnabled,
            feeds = updatedFeedsEnabled,
            posts = updatedPostsEnabled
        ).also {
            areFeedsEnabled = updatedFeedsEnabled
            arePostsEnabled = updatedPostsEnabled
        }
    }

    AlertDialogPicker(
        title = stringResource(id = StringResources.export_strategy_picker_title),
        saveButtonLabel = stringResource(id = StringResources.export_strategy_picker_export),
        cancelButtonLabel = stringResource(id = StringResources.export_strategy_picker_cancel),
        icon = DrawableResources.ic_upload_24,
        onCancel = { resultBackNavigator.navigateBack(result = null) },
        onSave = { resultBackNavigator.navigateBack(result = exportStrategy.toResults()) },
        isSaveButtonEnabled = areCategoriesEnabled,
        beforeDividerContent = {
            Text(
                text = stringResource(id = StringResources.export_strategy_picker_description),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            CheckboxWithText(
                text = stringResource(id = StringResources.export_strategy_picker_categories),
                selected = exportStrategy.categories,
                onSelect = { areCategoriesEnabled = it }
            )

            CheckboxWithText(
                text = stringResource(id = StringResources.export_strategy_picker_feeds),
                selected = exportStrategy.feeds,
                onSelect = { areFeedsEnabled = it },
                enabled = exportStrategy.isFeedExportAvailable
            )

            CheckboxWithText(
                text = stringResource(id = StringResources.export_strategy_picker_posts),
                selected = exportStrategy.posts,
                onSelect = { arePostsEnabled = it },
                enabled = exportStrategy.isPostsExportAvailable
            )
        }
    }
}