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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.core.components.ArrowBack
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.ResponseError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFeedBody(
    title: String,
    link: String,
    isEditMode: Boolean = false,
    iconLoadState: LoadState<Bitmap>,
    finishLoadState: LoadState<Boolean>,
    onTitleChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(iconLoadState) {
        iconLoadState.error?.let {
            snackbarHostState.showSnackbar(it.asLocalizedMessage(context))
        }
    }

    LaunchedEffect(finishLoadState) {
        finishLoadState.error?.let {
            snackbarHostState.showSnackbar(it.asLocalizedMessage(context))
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
                                R.string.add_edit_feed_create_feed
                            }
                        )
                    )
                },
                navigationIcon = { ArrowBack(onBackClick) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFinishClick
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
        }
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
                    capitalization = KeyboardCapitalization.Words
                )
            )

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                value = link,
                onValueChange = onLinkChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = stringResource(id = R.string.add_edit_feed_link)) },
                placeholder = { Text(text = stringResource(id = R.string.add_edit_feed_link_hint)) },
                trailingIcon = { FeedIcon(iconLoadState = iconLoadState) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Uri
                )
            )
        }
    }
}

@Composable
private fun FeedIcon(
    iconLoadState: LoadState<Bitmap>
) {
    when {
        iconLoadState.isLoading -> CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp
        )
        iconLoadState.data != null -> {
            Image(
                bitmap = iconLoadState.data.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AddEditFeedBodyPreview() {
    val context = LocalContext.current
    val icon = AppCompatResources.getDrawable(context, R.drawable.preview_favicon)?.toBitmap()

    AddEditFeedBody(
        title = "Rozetked",
        link = "https://rozetked.me/turbo",
        isEditMode = true,
        iconLoadState = icon?.let { LoadState.data(it) }
            ?: LoadState.error(ResponseError.UnknownError()),
        finishLoadState = LoadState.initial(),
        onTitleChange = {},
        onLinkChange = {},
        onFinishClick = {},
        onBackClick = {}
    )
}