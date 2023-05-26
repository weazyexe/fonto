package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedToolbar(
    scrollBehavior: TopAppBarScrollBehavior,
    lazyListState: LazyListState,
    onSearchClick: () -> Unit,
    onManageFeedClick: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LargeTopAppBar(
        title = { Text(text = stringResource(id = StringResources.home_bottom_label_feed)) },
        modifier = Modifier.clickable(
            onClick = {
                scope.launch { lazyListState.animateScrollToItem(0) }
            },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ),
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = DrawableResources.ic_search_24),
                    contentDescription = null
                )
            }
            IconButton(onClick = onManageFeedClick) {
                Icon(
                    painter = painterResource(id = DrawableResources.ic_controls_24),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}