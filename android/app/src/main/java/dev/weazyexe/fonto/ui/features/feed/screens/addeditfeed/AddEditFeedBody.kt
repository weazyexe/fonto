package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.components.Rotatable
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.toolbar.FullScreenDialogToolbar
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddEditFeedBody(
    title: String,
    link: String,
    category: Category?,
    categories: List<Category>,
    isEditMode: Boolean = false,
    snackbarHostState: SnackbarHostState,
    iconLoadState: LoadState<Bitmap?>,
    finishLoadState: LoadState<Unit>,
    onTitleChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onCategoryChange: (Category?) -> Unit,
    onAddCategoryClick: () -> Unit,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var isCategoriesExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(finishLoadState) {
        (finishLoadState as? LoadState.Error)?.let {
            snackbarHostState.showSnackbar(it.error.asLocalizedMessage(context))
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            FullScreenDialogToolbar(
                title = stringResource(
                    id = if (isEditMode) {
                        StringResources.add_edit_feed_edit_feed
                    } else {
                        StringResources.add_edit_feed_new_feed
                    }
                ),
                doneButtonText = stringResource(
                    if (isEditMode) {
                        StringResources.add_edit_feed_save
                    } else {
                        StringResources.add_edit_feed_create
                    }
                ),
                onBackClick = onBackClick,
                onDoneClick = onFinishClick
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = link,
                onValueChange = onLinkChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester),
                label = { Text(text = stringResource(id = StringResources.add_edit_feed_link)) },
                placeholder = { Text(text = stringResource(id = StringResources.add_edit_feed_link_hint)) },
                trailingIcon = { FeedIcon(iconLoadState = iconLoadState) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = stringResource(id = StringResources.add_edit_feed_title)) },
                placeholder = { Text(text = stringResource(id = StringResources.add_edit_feed_title_hint)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { isCategoriesExpanded = true }
                )
            )

            Spacer(modifier = Modifier.size(16.dp))

            ExposedDropdownMenuBox(
                expanded = isCategoriesExpanded,
                onExpandedChange = { isCategoriesExpanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = category?.title
                        ?: stringResource(id = StringResources.add_edit_feed_category_title_not_selected),
                    onValueChange = {},
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true,
                    label = { Text(text = stringResource(id = StringResources.add_edit_feed_category)) },
                    trailingIcon = {
                        Rotatable(isRotated = isCategoriesExpanded) {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = true)
                        }
                    },
                    maxLines = 1,
                    singleLine = true
                )

                DropdownMenu(
                    expanded = isCategoriesExpanded,
                    onDismissRequest = { isCategoriesExpanded = false },
                    modifier = Modifier.exposedDropdownSize()
                ) {
                    CategoryDropdownItem(
                        title = stringResource(id = StringResources.add_edit_feed_add_new_category),
                        isSelected = false,
                        isAddItem = true,
                        onClick = {
                            onAddCategoryClick()
                            isCategoriesExpanded = false
                        }
                    )

                    CategoryDropdownItem(
                        title = stringResource(id = StringResources.add_edit_feed_category_title_not_selected),
                        isSelected = category == null,
                        isAddItem = false,
                        onClick = {
                            onCategoryChange(null)
                            isCategoriesExpanded = false
                        }
                    )

                    categories.forEach {
                        CategoryDropdownItem(
                            title = it.title,
                            isSelected = it == category,
                            isAddItem = false,
                            onClick = {
                                onCategoryChange(it)
                                isCategoriesExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeedIcon(
    iconLoadState: LoadState<Bitmap?>
) {
    LoadStateComponent(
        loadState = iconLoadState,
        onSuccess = {
            val icon = it
            if (icon != null) {
                Image(
                    bitmap = icon.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        },
        onError = {
            // Do nothing
        },
        onLoading = {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
        }
    )
}

@Composable
private fun CategoryDropdownItem(
    title: String,
    isSelected: Boolean,
    isAddItem: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = isSelected || isAddItem) {
            when {
                isSelected -> {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_done_24),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                isAddItem -> {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_add_24),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Text(
            text = title,
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun AddEditFeedBodyPreview() = dev.weazyexe.fonto.core.ui.theme.ThemedPreview {
    val context = LocalContext.current
    val icon = AppCompatResources.getDrawable(context, DrawableResources.preview_favicon)?.toBitmap()

    AddEditFeedBody(
        title = "Rozetked",
        link = "https://rozetked.me/turbo",
        isEditMode = true,
        category = null,
        categories = listOf(),
        snackbarHostState = SnackbarHostState(),
        iconLoadState = LoadState.Data(icon),
        finishLoadState = LoadState.Data(Unit),
        onTitleChange = {},
        onCategoryChange = {},
        onLinkChange = {},
        onAddCategoryClick = {},
        onFinishClick = {},
        onBackClick = {}
    )
}
