package dev.weazyexe.fonto.ui.features.settings.screens.themepicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.components.preferences.SingleValuePickerDialog
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.util.stringRes

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun ThemePickerDialog(
    args: ThemePickerArgs,
    resultBackNavigator: ResultBackNavigator<Theme?>
) {
    SingleValuePickerDialog(
        value = Value(args.value, stringResource(id = args.value.stringRes)),
        possibleValues = args.possibleValues.map {
            Value(it, stringResource(id = it.stringRes))
        },
        icon = args.icon,
        title = args.title,
        onSave = { resultBackNavigator.navigateBack(result = it.data) },
        onCancel = { resultBackNavigator.navigateBack(result = null) }
    )
}