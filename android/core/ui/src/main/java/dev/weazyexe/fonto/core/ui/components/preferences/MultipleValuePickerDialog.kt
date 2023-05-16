package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value

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

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = { onSave(currentValues.toMutableList()) }) {
                Text(
                    text = stringResource(
                        id = if (hasDataToPick) {
                            R.string.value_picker_save
                        } else {
                            R.string.value_picker_ok
                        }
                    )
                )
            }
        },
        dismissButton = {
            if (hasDataToPick) {
                TextButton(onClick = onCancel) {
                    Text(text = stringResource(id = R.string.value_picker_cancel))
                }
            }
        },
        title = { Text(text = stringResource(id = title)) },
        text = {
            if (hasDataToPick) {
                Column(modifier = Modifier.heightIn(max = 384.dp)) {

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .weight(1f, fill = false)
                    ) {
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

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            } else {
                Text(text = stringResource(id = R.string.value_picker_no_possible_values))
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
private fun CheckboxWithText(
    text: String,
    selected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onSelect(!selected) })
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = selected,
            onCheckedChange = onSelect
        )
        Text(text = text)
    }
}