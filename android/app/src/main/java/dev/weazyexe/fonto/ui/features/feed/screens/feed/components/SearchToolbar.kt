package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SearchToolbar(
    query: String,
    isActive: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = isActive,
        onActiveChange = onActiveChange,
        modifier = modifier,
        placeholder = { Text("Search posts") },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = DrawableResources.ic_search_24),
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = isActive,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(onClick = { onActiveChange(false) }) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_close_24),
                        contentDescription = null
                    )
                }
            }
        },

    ) {
        Box(modifier = Modifier.fillMaxWidth())
    }
}