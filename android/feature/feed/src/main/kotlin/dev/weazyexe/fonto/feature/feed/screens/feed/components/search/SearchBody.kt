package dev.weazyexe.fonto.feature.feed.screens.feed.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState
import dev.weazyexe.fonto.core.ui.components.filters.FiltersRow
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.feature.feed.components.panes.EmptyQueryPane
import dev.weazyexe.fonto.feature.feed.components.panes.NotFoundPane
import dev.weazyexe.fonto.feature.feed.components.post.PostItem
import dev.weazyexe.fonto.feature.feed.components.post.PostItemType
import dev.weazyexe.fonto.feature.feed.components.post.PostViewState

@Composable
fun SearchBody(
    query: String,
    posts: AsyncResult<List<PostViewState>>,
    filters: List<FilterViewState<PostsFilter>>,
    isActive: Boolean,
    areFiltersChanged: Boolean,
    contentPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onFilterChange: (PostsFilter) -> Unit,
    openDateRangePickerDialog: (PostsFilter) -> Unit,
    openMultiplePickerDialog: (PostsFilter) -> Unit,
    onPostClick: (Post.Id) -> Unit,
    onPostSaveClick: (Post.Id) -> Unit,
    loadPostMetadata: (Post.Id) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        AnimatedVisibility(visible = !isActive) {
            Spacer(modifier = Modifier.size(16.dp))
        }

        SearchBarAndResults(
            query = query,
            posts = posts,
            filters = filters,
            isActive = isActive,
            areFiltersChanged = areFiltersChanged,
            contentPadding = contentPadding,
            snackbarHostState = snackbarHostState,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            onActiveChange = onActiveChange,
            onFilterChange = onFilterChange,
            openDateRangePickerDialog = openDateRangePickerDialog,
            openMultiplePickerDialog = openMultiplePickerDialog,
            onPostClick = onPostClick,
            onPostSaveClick = onPostSaveClick,
            loadPostMetadata = loadPostMetadata,
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(visible = !isActive) {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarAndResults(
    query: String,
    isActive: Boolean,
    posts: AsyncResult<List<PostViewState>>,
    filters: List<FilterViewState<PostsFilter>>,
    areFiltersChanged: Boolean,
    contentPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onFilterChange: (PostsFilter) -> Unit,
    openDateRangePickerDialog: (PostsFilter) -> Unit,
    openMultiplePickerDialog: (PostsFilter) -> Unit,
    onPostClick: (Post.Id) -> Unit,
    onPostSaveClick: (Post.Id) -> Unit,
    loadPostMetadata: (Post.Id) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { onSearch() },
        active = isActive,
        onActiveChange = onActiveChange,
        modifier = modifier,
        placeholder = { Text(stringResource(id = StringResources.feed_search_hint)) },
        leadingIcon = {
            ScaleAnimatedVisibility(isVisible = isActive) {
                IconButton(
                    onClick = { onActiveChange(false) },
                    modifier = Modifier.testTag("close_search_button")
                ) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_arrow_back_24),
                        contentDescription = null
                    )
                }
            }

            ScaleAnimatedVisibility(isVisible = !isActive) {
                IconButton(
                    onClick = { onActiveChange(true) },
                    modifier = Modifier.testTag("search_button")
                ) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_search_24),
                        contentDescription = null
                    )
                }
            }
        },
        trailingIcon = {
            ScaleAnimatedVisibility(isVisible = isActive && query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_close_24),
                        contentDescription = null
                    )
                }
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            Column {
                FiltersRow(
                    filters = filters,
                    onFilterChange = { onFilterChange(it as PostsFilter) },
                    openDateRangePickerDialog = { openDateRangePickerDialog(it as PostsFilter) },
                    openMultiplePickerDialog = { openMultiplePickerDialog(it as PostsFilter) }
                )

                when (posts) {
                    is AsyncResult.Loading -> LoadingPane(modifier = Modifier.fillMaxSize())
                    is AsyncResult.Error -> ErrorPane(params = posts.error.asErrorPaneParams())
                    is AsyncResult.Success -> {
                        val posts = posts.data
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
                                posts = posts,
                                contentPadding = contentPadding,
                                onPostClick = onPostClick,
                                onSaveClick = onPostSaveClick,
                                loadPostMetadata = loadPostMetadata
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(contentPadding.calculateBottomPadding()))
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = contentPadding.calculateBottomPadding())
            )
        }
    }
}

@Composable
private fun PostsList(
    posts: List<PostViewState>,
    contentPadding: PaddingValues,
    onPostClick: (Post.Id) -> Unit,
    onSaveClick: (Post.Id) -> Unit,
    loadPostMetadata: (Post.Id) -> Unit,
) {
    if (posts.isEmpty()) {
        NotFoundPane(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn {
            items(
                items = posts,
                key = { it.id.origin }
            ) { post ->
                PostItem(
                    post = post,
                    type = PostItemType.COMPACT,
                    onPostClick = { onPostClick(post.id) },
                    onSaveClick = { onSaveClick(post.id) },
                    loadPostMetadata = loadPostMetadata
                )
            }
            item(key = "bottom_padding") {
                Spacer(modifier = Modifier.size(contentPadding.calculateBottomPadding()))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleAnimatedVisibility(isVisible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        content()
    }
}
