package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post

class UpdatePostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(post: Post) {
        postRepository.insertOrUpdate(post)
    }
}