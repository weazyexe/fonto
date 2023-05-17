package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.FontoTextButton
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.ResponseError
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview

@Composable
fun AddEditCategoryBody(
    title: String,
    isEditMode: Boolean,
    savingLoadState: LoadState<Unit>,
    onTitleChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            FontoTextButton(
                title = stringResource(
                    if (isEditMode) {
                        R.string.add_edit_category_save
                    } else {
                        R.string.add_edit_category_create
                    }
                ),
                onClick = onSaveClick,
                isLoading = savingLoadState is LoadState.Loading
            )
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text(text = stringResource(id = R.string.value_picker_cancel))
            }
        },
        title = {
            Text(
                text = stringResource(
                    id = if (isEditMode) {
                        R.string.add_edit_category_edit
                    } else {
                        R.string.add_edit_category_new
                    }
                )
            )
        },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.add_edit_category_title)) },
                placeholder = { Text(text = stringResource(id = R.string.add_edit_category_title_hint)) },
                maxLines = 1,
                singleLine = true,
                isError = savingLoadState is LoadState.Error,
                supportingText = {
                    if (savingLoadState is LoadState.Error) {
                        Text(
                            text = savingLoadState.error.asLocalizedMessage(context),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSaveClick() }
                )
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_category_24),
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun AddEditCategoryPreview() = ThemedPreview {
    AddEditCategoryBody(
        title = "",
        isEditMode = true,
        savingLoadState = LoadState.Data(Unit),
        onTitleChange = {},
        onCancelClick = {},
        onSaveClick = {}
    )
}

@Preview
@Composable
private fun AddEditCategoryErrorPreview() = ThemedPreview {
    AddEditCategoryBody(
        title = "",
        isEditMode = false,
        savingLoadState = LoadState.Error(ResponseError.NoInternetError()),
        onTitleChange = {},
        onCancelClick = {},
        onSaveClick = {}
    )
}