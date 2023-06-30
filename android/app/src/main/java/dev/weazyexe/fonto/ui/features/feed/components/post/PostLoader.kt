package dev.weazyexe.fonto.ui.features.feed.components.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.components.loadstate.fadeLoader
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview

@Composable
fun PostLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fadeLoader(1.dp)
    ) {
        TitleLoader()
        BodyLoader()
        ImageLoader()
    }
}

@Composable
private fun TitleLoader() {
    val textShape = RoundedCornerShape(2.dp)
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .fadeLoader(5.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .clip(textShape)
                    .size(width = 120.dp, height = 16.dp)
                    .fadeLoader(5.dp)
            )

            Box(
                modifier = Modifier
                    .clip(textShape)
                    .size(width = 80.dp, height = 14.dp)
                    .fadeLoader(5.dp)
            )
        }
    }
}

@Composable
private fun BodyLoader() {
    val textShape = RoundedCornerShape(2.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth()
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth(0.8f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth(0.9f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth(0.8f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth(1f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(textShape)
                .fillMaxWidth(0.7f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .clip(textShape)
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .fadeLoader(5.dp)
        )
    }
}

@Composable
private fun ImageLoader() {
    Box(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
            .height(240.dp)
            .fadeLoader(5.dp)
    )
}

@Preview
@Composable
private fun PostLoaderPreview() {
    ThemedPreview {
        PostLoader(modifier = Modifier.fillMaxWidth())
    }
}