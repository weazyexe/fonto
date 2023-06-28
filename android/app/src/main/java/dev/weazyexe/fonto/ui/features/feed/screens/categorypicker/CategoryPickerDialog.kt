package dev.weazyexe.fonto.ui.features.feed.screens.categorypicker

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.components.preferences.MultipleValuePickerDialog
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun CategoryPickerDialog(
    args: CategoryPickerArgs,
    resultBackNavigator: ResultBackNavigator<CategoryPickerResults?>
) {
    MultipleValuePickerDialog(
        values = args.values.map { Value(it, it.title) },
        possibleValues = args.possibleValues.map { Value(it, it.title) },
        icon = DrawableResources.ic_category_24,
        title = args.title,
        onSave = { results ->
            resultBackNavigator.navigateBack(
                result = CategoryPickerResults(
                    values = results.map { it.data },
                    possibleValues = args.possibleValues
                )
            )
        },
        onCancel = { resultBackNavigator.navigateBack(result = null) }
    )
}