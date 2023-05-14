package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.CloseDialogButton
import dev.weazyexe.fonto.core.ui.components.LoadStateComponent
import dev.weazyexe.fonto.core.ui.presentation.LoadState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFeedBody(
    title: String,
    link: String,
    isEditMode: Boolean = false,
    iconLoadState: LoadState<Bitmap?>,
    finishLoadState: LoadState<Unit>,
    onTitleChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(iconLoadState) {
        (iconLoadState as? LoadState.Error)?.let {
            snackbarHostState.showSnackbar(it.error.asLocalizedMessage(context))
        }
    }

    LaunchedEffect(finishLoadState) {
        (finishLoadState as? LoadState.Error)?.let {
            snackbarHostState.showSnackbar(it.error.asLocalizedMessage(context))
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = if (isEditMode) {
                                R.string.add_edit_feed_edit_feed
                            } else {
                                R.string.add_edit_feed_new_feed
                            }
                        )
                    )
                },
                navigationIcon = { CloseDialogButton(onBackClick) },
                actions = {
                    TextButton(onClick = onFinishClick) {
                        Text(
                            text = stringResource(
                                if (isEditMode) {
                                    R.string.add_edit_feed_save
                                } else {
                                    R.string.add_edit_feed_create
                                }
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        elevation = 3.dp
                    )
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = stringResource(id = R.string.add_edit_feed_title)) },
                placeholder = { Text(text = stringResource(id = R.string.add_edit_feed_title_hint)) },
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
                value = link,
                onValueChange = onLinkChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester),
                label = { Text(text = stringResource(id = R.string.add_edit_feed_link)) },
                placeholder = { Text(text = stringResource(id = R.string.add_edit_feed_link_hint)) },
                trailingIcon = { FeedIcon(iconLoadState = iconLoadState) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onFinishClick() }
                )
            )
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

@Preview
@Composable
private fun AddEditFeedBodyPreview() = dev.weazyexe.fonto.core.ui.theme.ThemedPreview {
    val context = LocalContext.current
    val icon = AppCompatResources.getDrawable(context, R.drawable.preview_favicon)?.toBitmap()

    AddEditFeedBody(
        title = "Rozetked",
        link = "https://rozetked.me/turbo",
        isEditMode = true,
        iconLoadState = LoadState.Data(icon),
        finishLoadState = LoadState.Data(Unit),
        onTitleChange = {},
        onLinkChange = {},
        onFinishClick = {},
        onBackClick = {}
    )
}
