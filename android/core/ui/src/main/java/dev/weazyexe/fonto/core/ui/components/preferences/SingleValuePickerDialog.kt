package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Composable
fun <T> SingleValuePickerDialog(
    value: Value<T>,
    possibleValues: List<Value<T>>,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    onSave: (Value<T>) -> Unit,
    onCancel: () -> Unit
) {
    var currentValue by remember { mutableStateOf(value) }

    AlertDialogPicker(
        title = stringResource(id = title),
        saveButtonLabel = stringResource(id = StringResources.value_picker_save),
        cancelButtonLabel = stringResource(id = StringResources.value_picker_cancel),
        icon = icon,
        onCancel = onCancel,
        onSave = { onSave(currentValue) }
    ) {
        Column {
            possibleValues.forEach {
                RadioWithText(
                    text = it.title,
                    selected = it == currentValue,
                    onSelect = {
                        currentValue = it
                    }
                )
            }
        }
    }
}