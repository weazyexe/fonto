package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.components.PaginationFooter
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.post.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostsViewState

fun LazyListScope.buildPosts(
    posts: PostsViewState,
    paginationState: PaginationState,
    paddingBottom: Dp,
    onManageFeed: () -> Unit,
    onPostClick: (Post.Id) -> Unit,
    onPostSaveClick: (Post.Id) -> Unit,
    onPostLoadImage: (Post.Id) -> Unit,
    loadMore: () -> Unit
) {
    if (posts.isEmpty()) {
        item(key = "error pane") {
            ErrorPane(
                params = ErrorPaneParams.empty(
                    message = StringResources.feed_empty_posts,
                    action = ErrorPaneParams.Action(
                        title = StringResources.feed_empty_posts_manage_feed,
                        onClick = onManageFeed
                    )
                )
            )
        }
    } else {
        items(items = posts, key = { it.id.origin }) {
            PostItem(
                post = it,
                onPostClick = { onPostClick(it.id) },
                onSaveClick = { onPostSaveClick(it.id) },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .testTag("post_item"),
                onPostLoadImage = onPostLoadImage
            )
        }

        item(key = "footer") {
            PaginationFooter(
                state = paginationState,
                onRefresh = loadMore
            )
        }

        item(key = "bottom_padding") {
            Box(modifier = Modifier.size(paddingBottom))
        }
    }
}