package dev.weazyexe.fonto.ui.features.settings.screens.themepicker

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.components.preferences.ValuePickerDialog

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun ThemePickerDialog(
    args: ThemePickerArgs,
    resultBackNavigator: ResultBackNavigator<Theme?>
) {
    ValuePickerDialog(
        value = args.value,
        possibleValues = args.possibleValues,
        icon = args.icon,
        title = args.title,
        onSave = { resultBackNavigator.navigateBack(result = it.data) },
        onCancel = { resultBackNavigator.navigateBack(result = null) }
    )
}