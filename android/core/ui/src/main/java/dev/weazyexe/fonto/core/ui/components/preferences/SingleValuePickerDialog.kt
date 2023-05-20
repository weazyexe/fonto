package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = { onSave(currentValue) }) {
                Text(text = stringResource(id = StringResources.value_picker_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = StringResources.value_picker_cancel))
            }
        },
        title = { Text(text = stringResource(id = title)) },
        text = {
            Column {
                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                possibleValues.forEach {
                    RadioWithText(
                        text = it.title,
                        selected = it == currentValue,
                        onSelect = {
                            currentValue = it
                        }
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    )
}

@Composable
private fun RadioWithText(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onSelect)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Text(text = text)
    }
}