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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.core.ui.components.FontoTextButton
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.coroutines.delay

@Composable
fun AddEditCategoryBody(
    title: String,
    isEditMode: Boolean,
    savingLoadState: LoadState<Unit>,
    initLoadState: LoadState<Unit>,
    onTitleChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = title))
    }

    LaunchedEffect(initLoadState) {
        if (initLoadState is LoadState.Data) {
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
                isLoading = savingLoadState is LoadState.Loading
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
                isError = savingLoadState is LoadState.Error,
                supportingText = {
                    if (savingLoadState is LoadState.Error) {
                        // FIXME #35
                        Text(
                            text = savingLoadState.error.localizedMessage.orEmpty(),
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
        savingLoadState = LoadState.Data(Unit),
        initLoadState = LoadState.Data(Unit),
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
        savingLoadState = LoadState.Error(ResponseError.NoInternetError),
        initLoadState = LoadState.Data(Unit),
        onTitleChange = {},
        onCancelClick = {},
        onSaveClick = {}
    )
}