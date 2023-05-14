package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.preferences.MultipleValuePickerDialog
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun FeedPickerDialog(
    args: FeedPickerArgs,
    resultBackNavigator: ResultBackNavigator<FeedPickerResult?>
) {
    MultipleValuePickerDialog(
        values = args.values.map { Value(it, it.title) },
        possibleValues = args.possibleValues.map { Value(it, it.title) },
        icon = R.drawable.ic_feed_24,
        title = args.title,
        onSave = { results ->
            resultBackNavigator.navigateBack(
                result = FeedPickerResult(
                    values = results.map { it.data },
                    possibleValues = args.possibleValues
                )
            )
        },
        onCancel = { resultBackNavigator.navigateBack(result = null) }
    )
}