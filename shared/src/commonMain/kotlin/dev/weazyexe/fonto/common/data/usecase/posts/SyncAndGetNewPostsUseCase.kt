package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedsWithNotificationsUseCase
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.awaitSuccess
import kotlinx.datetime.Clock

internal class SyncAndGetNewPostsUseCase(
    private val getAllPosts: GetAllPostsUseCase,
    private val syncPosts: SyncPostsUseCase,
    private val getFeedsWithNotifications: GetFeedsWithNotificationsUseCase
) {

    suspend operator fun invoke(): AsyncResult<List<Post>> {
        val timestampBeforeSync = Clock.System.now()
        syncPosts()

        val posts = getAllPosts().awaitSuccess() ?: run {
            return AsyncResult.Error(ResponseError.UnknownError)
        }

        val feeds = getFeedsWithNotifications().map { it.id }
        val newPosts = posts.data
            .filter { it.addedAt > timestampBeforeSync }
            .filter { it.feed.id in feeds }

        return AsyncResult.Success(newPosts)
    }
}