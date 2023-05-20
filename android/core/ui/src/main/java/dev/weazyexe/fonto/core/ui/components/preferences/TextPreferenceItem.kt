package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Composable
fun TextPreferenceItem(
    title: String,
    description: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextPreferenceItemPreview() = ThemedPreview {
    TextPreferenceItem(
        title = "Debug menu",
        description = "Settings for more efficient development",
        icon = DrawableResources.ic_bug_24,
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}