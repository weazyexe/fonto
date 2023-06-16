package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.vibration.vibrate

@Composable
fun SwitchPreferenceItem(
    title: String,
    description: String,
    value: Boolean,
    @DrawableRes icon: Int,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .clickable(
                onClick = {
                    context.vibrate()
                    onValueChange(!value)
                }
            )
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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
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

        Switch(
            checked = value,
            onCheckedChange = {
                context.vibrate()
                onValueChange(it)
            },
            colors = SwitchDefaults.colors(checkedIconColor = MaterialTheme.colorScheme.primary),
            thumbContent = {
                if (value) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_done_24),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SwitchPreferenceItemPreview() = ThemedPreview {
    SwitchPreferenceItem(
        title = "Something",
        description = "Enabled or disabled",
        value = true,
        icon = DrawableResources.ic_bookmark_24,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun SwitchPreferenceItemDisabledPreview() = ThemedPreview {
    SwitchPreferenceItem(
        title = "Something",
        description = "Lorem ipsum dolor set very long text very long text very long text",
        value = false,
        icon = DrawableResources.ic_bookmark_24,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}