package dev.weazyexe.fonto.ui.features.feed.components.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.PluralResources
import dev.weazyexe.fonto.ui.features.feed.preview.CategoryViewStatePreview

@Composable
fun CategoryItem(
    category: CategoryViewState,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = category.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = pluralStringResource(
                    id = PluralResources.categories_amount_of_feeds,
                    count = category.amountOfFeeds,
                    category.amountOfFeeds
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.weight(weight = 1f))

        IconButton(onClick = onDeleteClick) {
            Icon(
                painter = painterResource(id = DrawableResources.ic_delete_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() = ThemedPreview {
    CategoryItem(
        category = CategoryViewStatePreview.default,
        onClick = { },
        onDeleteClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemNoFeedsPreview() = ThemedPreview {
    CategoryItem(
        category = CategoryViewStatePreview.noFeeds,
        onClick = { },
        onDeleteClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}