package dev.weazyexe.fonto.common.app.background.sync

import dev.weazyexe.fonto.common.app.background.Worker
import dev.weazyexe.fonto.common.app.background.WorkerResult
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.usecase.notification.CreateNotificationUseCase
import dev.weazyexe.fonto.common.data.usecase.notification.GetLastNotificationUseCase
import dev.weazyexe.fonto.common.data.usecase.notification.ShowNotificationUseCase
import dev.weazyexe.fonto.common.data.usecase.notification.UpdateNotificationUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.SyncAndGetNewPostsUseCase
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import io.github.aakira.napier.Napier

internal class SyncPostsWorker(
    private val syncAndGetNewPostsUseCase: SyncAndGetNewPostsUseCase,
    private val getLastNotification: GetLastNotificationUseCase,
    private val createNotification: CreateNotificationUseCase,
    private val updateNotification: UpdateNotificationUseCase,
    private val showNotification: ShowNotificationUseCase
) : Worker {

    override suspend fun doWork(): WorkerResult {
        val posts = syncAndGetNewPostsUseCase().successOrThrow()
        Napier.d { "Worker ${posts.data}" }
        val lastNotification = getOrCreateNotification()
        val updatedNotification = updateNotification(lastNotification, posts.data)
            ?: return WorkerResult.FAILURE

        if (posts.data.isNotEmpty()) {
            showNotification(updatedNotification)
        }
        return WorkerResult.SUCCESS
    }

    private suspend fun getOrCreateNotification(): Notification {
        return getLastNotification().let { notification ->
            val meta = NotificationMeta.NewPosts(posts = emptyList())
            notification ?: createNotification(meta)
        }
    }

    private fun updateNotification(notification: Notification, posts: List<Post>): Notification? {
        val postIds = posts.map { it.id }
        val updatedPosts = (notification.meta as? NotificationMeta.NewPosts)
            ?.posts?.plus(postIds) ?: return null
        val updatedMeta = NotificationMeta.NewPosts(updatedPosts)
        val updatedNotification = notification.copy(meta = updatedMeta)
        updateNotification(notification = updatedNotification)
        return updatedNotification
    }

    private fun <T> AsyncResult<T>.successOrThrow(): AsyncResult.Success<T> {
        return when (this) {
            is AsyncResult.Loading<*> -> {
                throw IllegalStateException(
                    "SyncAndGetNewPostsUseCase should not have AsyncResult.Loading state. " +
                            "Return Success or Error instead"
                )
            }

            is AsyncResult.Error -> {
                throw this.error
            }

            is AsyncResult.Success -> {
                this
            }
        }
    }
}