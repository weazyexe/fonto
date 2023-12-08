package dev.weazyexe.fonto.android.feature.feed.screens.feedpicker

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.components.preferences.MultipleValuePickerDialog
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun FeedPickerDialog(
    args: FeedPickerArgs,
    resultBackNavigator: ResultBackNavigator<FeedPickerResults?>
) {
    MultipleValuePickerDialog(
        values = args.values.map { Value(it, it.title) },
        possibleValues = args.possibleValues.map { Value(it, it.title) },
        icon = DrawableResources.ic_feed_24,
        title = args.title,
        onSave = { results ->
            resultBackNavigator.navigateBack(
                result = FeedPickerResults(
                    values = results.map { it.data },
                    possibleValues = args.possibleValues
                )
            )
        },
        onCancel = { resultBackNavigator.navigateBack(result = null) }
    )
}
