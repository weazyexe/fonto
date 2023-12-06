package dev.weazyexe.fonto.feature.feed.screens.deleteconfirmation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.components.DeleteConfirmationDialog
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun FeedDeleteConfirmationDialog(
    resultBackNavigator: ResultBackNavigator<Long?>,
    feedId: Feed.Id,
    feedTitle: String
) {
    DeleteConfirmationDialog(
        title = stringResource(id = StringResources.feed_delete_confirmation_title),
        description = buildAnnotatedString {
            append(stringResource(id = StringResources.feed_delete_confirmation_description_first))
            append(" ")
            append(AnnotatedString(feedTitle, SpanStyle(fontWeight = FontWeight.Bold)))
            append(" ")
            append(stringResource(id = StringResources.feed_delete_confirmation_description_second))
        },
        deleteButtonText = stringResource(id = StringResources.feed_delete_confirmation_delete),
        cancelButtonText = stringResource(id = StringResources.feed_delete_confirmation_cancel),
        onDeleteClick = {
            resultBackNavigator.navigateBack(result = feedId.origin)
        },
        onCancelClick = {
            resultBackNavigator.navigateBack(result = null)
        }
    )
}
