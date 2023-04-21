package dev.weazyexe.fonto.ui.features.feed.screens.post

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State

@Immutable
data class PostState(
    val post: LoadState<Post> = LoadState.Loading()
) : State

sealed interface PostEffect : Effect
