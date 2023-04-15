package dev.weazyexe.fonto.ui.features.feed.screens.deleteconfirmation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.ui.core.components.AnimatedAppearing

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DeleteConfirmationDialog(
    resultBackNavigator: ResultBackNavigator<Long?>,
    feed: Feed,
) {
    AnimatedAppearing {
        AlertDialog(
            onDismissRequest = { resultBackNavigator.navigateBack(result = null) },
            confirmButton = {
                FilledTonalButton(
                    onClick = { resultBackNavigator.navigateBack(result = feed.id) },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(text = stringResource(id = R.string.feed_delete_confirmation_delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { resultBackNavigator.navigateBack(result = null) }) {
                    Text(text = stringResource(id = R.string.feed_delete_confirmation_cancel))
                }
            },
            title = { Text(text = stringResource(id = R.string.feed_delete_confirmation_title)) },
            text = {
                val text = buildAnnotatedString {
                    append(stringResource(id = R.string.feed_delete_confirmation_description_first))
                    append(" ")
                    append(AnnotatedString(feed.title, SpanStyle(fontWeight = FontWeight.Bold)))
                    append(" ")
                    append(stringResource(id = R.string.feed_delete_confirmation_description_second))
                }
                Text(text = text)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_forever_24),
                    contentDescription = null
                )
            }
        )
    }
}