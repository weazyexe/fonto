package dev.weazyexe.fonto.android.feature.feed.components.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Composable
fun FeedIcon(
    icon: ImageBitmap?,
    modifier: Modifier = Modifier
) {
    val imageModifier = modifier
        .size(36.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.inverseOnSurface)
        .padding(10.dp)

    if (icon != null) {
        Image(
            bitmap = icon,
            contentDescription = null,
            modifier = imageModifier
        )
    } else {
        Image(
            painter = painterResource(id = DrawableResources.ic_feed_24),
            contentDescription = null,
            modifier = imageModifier,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
        )
    }
}
