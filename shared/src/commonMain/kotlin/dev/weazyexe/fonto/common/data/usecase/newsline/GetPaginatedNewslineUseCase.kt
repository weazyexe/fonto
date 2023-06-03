package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetPaginatedNewslineUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Newsline {
        return Newsline.Success(
            posts = postRepository.getPosts(
                limit = limit,
                offset = offset
            ),
            loadedWithError = emptyList()
        )
    }
}
