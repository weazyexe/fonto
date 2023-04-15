package dev.weazyexe.fonto.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.R

@Composable
fun ArrowBack(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back_24),
            contentDescription = stringResource(id = R.string.accessibility_go_back)
        )
    }
}