package dev.weazyexe.fonto.feature.feed.screens.feed

import dev.weazyexe.elm.Update
import dev.weazyexe.elm.effects.Effect
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.common.model.feed.Posts as PostsModel

data class FeedState(
    val posts: AsyncResult<PostsModel> = AsyncResult.Loading(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val isSwipeRefreshing: Boolean = false,
    val limit: Int = DEFAULT_LIMIT,
    val offset: Int = 0,
    val isSearchBarActive: Boolean = false,
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemOffset: Int = 0
) {

    val postsList: PostsModel?
        get() = (posts as? AsyncResult.Success)?.data
}

sealed interface FeedMessage {

    data class OpenPost(
        val post: Post,
        val theme: Theme,
        val openPostPreference: OpenPostPreference
    ) : FeedMessage

    sealed interface View : FeedMessage {

        data class OnPostClick(val id: Post.Id) : View

        data object OnRefresh : View

        data object OnLoadMore : View
    }

    sealed interface Request : FeedMessage {

        data class UpdatingPost(val post: AsyncResult<Post>) : Request

        data class GettingPosts(val posts: AsyncResult<PostsModel>) : Request
    }
}

typealias FeedUpdate = Update<FeedState, FeedMessage, FeedDependencies>
typealias FeedEffect = Effect<FeedDependencies, FeedMessage>
