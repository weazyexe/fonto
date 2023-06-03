package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Composable
fun <T> MultipleValuePickerDialog(
    values: List<Value<T>>,
    possibleValues: List<Value<T>>,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    onSave: (List<Value<T>>) -> Unit,
    onCancel: () -> Unit
) {
    val scrollState = rememberScrollState()
    val currentValues = remember { values.toMutableStateList() }
    val hasDataToPick = possibleValues.isNotEmpty()

    AlertDialogPicker(
        title = stringResource(id = title),
        saveButtonLabel = stringResource(
            id = if (hasDataToPick) {
                StringResources.value_picker_save
            } else {
                StringResources.value_picker_ok
            }
        ),
        cancelButtonLabel = stringResource(id = StringResources.value_picker_cancel),
        icon = icon,
        hasCancelButton = hasDataToPick,
        onCancel = onCancel,
        onSave = { onSave(currentValues.toList()) }
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            possibleValues.forEach {
                CheckboxWithText(
                    text = it.title,
                    selected = it in currentValues,
                    onSelect = { isChecked ->
                        if (isChecked) {
                            currentValues.add(it)
                        } else {
                            currentValues.remove(it)
                        }
                    }
                )
            }
        }
    }
}