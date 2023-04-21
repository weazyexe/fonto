package dev.weazyexe.fonto.ui.features.feed.screens.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPostUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.destinations.PostScreenDestination
import kotlinx.coroutines.launch

class PostViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPost: GetPostUseCase
) : CoreViewModel<PostState, PostEffect>() {

    private val args = PostScreenDestination.argsFrom(savedStateHandle)

    override val initialState: PostState = PostState()

    init {
        loadPost(args.postId, args.feedId)
    }

    fun loadPost(postId: Post.Id, feedId: Feed.Id) = viewModelScope.launch {
        val post = request { getPost(postId, feedId) }
            .withErrorHandling {
                setState { copy(post = LoadState.Error(it)) }
            } ?: return@launch

        setState { copy(post = post) }
    }
}
