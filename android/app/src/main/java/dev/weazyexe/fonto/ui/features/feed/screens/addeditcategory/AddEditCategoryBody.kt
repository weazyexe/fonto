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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.core.ui.components.FontoTextButton
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.coroutines.delay

@Composable
fun AddEditCategoryBody(
    title: String,
    isEditMode: Boolean,
    savingResult: AsyncResult<Unit>,
    initResult: AsyncResult<Unit>,
    onTitleChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = title))
    }

    LaunchedEffect(initResult) {
        if (initResult is AsyncResult.Success) {
            if (title.isNotEmpty()) {
                textFieldValue = TextFieldValue(text = title, TextRange(index = title.length))
            }
            delay(200L)
            focusRequester.requestFocus()
        }
    }

    AlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            FontoTextButton(
                title = stringResource(
                    if (isEditMode) {
                        StringResources.add_edit_category_save
                    } else {
                        StringResources.add_edit_category_create
                    }
                ),
                onClick = onSaveClick,
                isLoading = savingResult is AsyncResult.Loading
            )
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text(text = stringResource(id = StringResources.value_picker_cancel))
            }
        },
        title = {
            Text(
                text = stringResource(
                    id = if (isEditMode) {
                        StringResources.add_edit_category_edit
                    } else {
                        StringResources.add_edit_category_new
                    }
                )
            )
        },
        text = {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = {
                    onTitleChange(it.text)
                    textFieldValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                label = { Text(text = stringResource(id = StringResources.add_edit_category_title)) },
                placeholder = { Text(text = stringResource(id = StringResources.add_edit_category_title_hint)) },
                maxLines = 1,
                singleLine = true,
                isError = savingResult is AsyncResult.Error,
                supportingText = {
                    if (savingResult is AsyncResult.Error) {
                        Text(
                            text = if (savingResult.error is ResponseError.InvalidTitle) {
                                stringResource(id = StringResources.add_edit_category_invalid_title)
                            } else {
                                stringResource(id = StringResources.add_edit_category_save_failure)
                            },
                            color = MaterialTheme.colorScheme.error,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(text = "", overflow = TextOverflow.Ellipsis)
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
                painter = painterResource(id = DrawableResources.ic_category_24),
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
        savingResult = AsyncResult.Success(Unit),
        initResult = AsyncResult.Success(Unit),
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
        savingResult = AsyncResult.Error(ResponseError.NoInternetError),
        initResult = AsyncResult.Success(Unit),
        onTitleChange = {},
        onCancelClick = {},
        onSaveClick = {}
    )
}