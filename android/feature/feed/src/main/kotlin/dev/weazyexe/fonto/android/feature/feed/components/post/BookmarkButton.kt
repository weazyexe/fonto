package dev.weazyexe.fonto.android.feature.feed.components.post

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Composable
fun BookmarkButton(
    isSaved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(
                id = if (isSaved) {
                    DrawableResources.ic_bookmark_added_24
                } else {
                    DrawableResources.ic_bookmark_24
                }
            ),
            contentDescription = null
        )
    }
}
