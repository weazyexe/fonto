package dev.weazyexe.fonto.ui.features.settings.screens.syncintervalpicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import dev.weazyexe.fonto.core.ui.components.preferences.SingleValuePickerDialog
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper.stringRes

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun SyncIntervalPickerDialog(
    args: SyncIntervalPickerArgs,
    resultBackNavigator: ResultBackNavigator<SyncPostsInterval?>
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