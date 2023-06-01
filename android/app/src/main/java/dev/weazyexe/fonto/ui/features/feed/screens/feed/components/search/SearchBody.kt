package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.components.filters.FiltersRow
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.ui.features.feed.components.panes.EmptyQueryPane
import dev.weazyexe.fonto.ui.features.feed.components.panes.NotFoundPane
import dev.weazyexe.fonto.ui.features.feed.components.post.PostCompactItem
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewStates

@Composable
fun SearchBody(
    query: String,
    postsLoadState: LoadState<List<Post>>,
    filters: List<NewslineFilter>,
    isActive: Boolean,
    areFiltersChanged: Boolean,
    contentPadding: PaddingValues,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onFilterChange: (NewslineFilter) -> Unit,
    openDateRangePickerDialog: (NewslineFilter) -> Unit,
    openMultiplePickerDialog: (NewslineFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        AnimatedVisibility(visible = !isActive) {
            Spacer(modifier = Modifier.size(16.dp))
        }

        SearchBarAndResults(
            query = query,
            postsLoadState = postsLoadState,
            filters = filters,
            isActive = isActive,
            areFiltersChanged = areFiltersChanged,
            contentPadding = contentPadding,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            onActiveChange = onActiveChange,
            onFilterChange = onFilterChange,
            openDateRangePickerDialog = openDateRangePickerDialog,
            openMultiplePickerDialog = openMultiplePickerDialog,
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(visible = !isActive) {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun SearchBarAndResults(
    query: String,
    isActive: Boolean,
    postsLoadState: LoadState<List<Post>>,
    filters: List<NewslineFilter>,
    areFiltersChanged: Boolean,
    contentPadding: PaddingValues,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onFilterChange: (NewslineFilter) -> Unit,
    openDateRangePickerDialog: (NewslineFilter) -> Unit,
    openMultiplePickerDialog: (NewslineFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { onSearch() },
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
        Column(modifier = Modifier.imePadding()) {
            FiltersRow(
                filters = filters.asViewStates(),
                onFilterChange = { onFilterChange(it as NewslineFilter) },
                openDateRangePickerDialog = { openDateRangePickerDialog(it as NewslineFilter) },
                openMultiplePickerDialog = { openMultiplePickerDialog(it as NewslineFilter) }
            )

            when (postsLoadState) {
                is LoadState.Loading -> LoadingPane()
                is LoadState.Error -> ErrorPane(params = postsLoadState.error.asErrorPaneParams())
                is LoadState.Data -> {
                    val posts = postsLoadState.data
                    when {
                        query.isEmpty() && !areFiltersChanged -> {
                            EmptyQueryPane(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        posts.isEmpty() -> {
                            NotFoundPane(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        else -> PostsList(
                            posts = posts.map { it.asViewState() },
                            contentPadding = contentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(contentPadding.calculateBottomPadding()))
        }
    }
}

@Composable
private fun PostsList(
    posts: List<PostViewState>,
    contentPadding: PaddingValues
) {
    if (posts.isEmpty()) {
        NotFoundPane(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn {
            items(
                items = posts,
                key = { it.id.origin }
            ) { post ->
                PostCompactItem(
                    post = post,
                    onPostClick = {},
                    onSaveClick = {}
                )
            }
            item {
                Spacer(modifier = Modifier.size(contentPadding.calculateBottomPadding()))
            }
        }
    }
}