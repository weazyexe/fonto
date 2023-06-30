package dev.weazyexe.fonto.common.app.background.sync

import dev.weazyexe.fonto.common.app.background.BackgroundTaskResult
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn

internal class SyncPostsBackgroundTaskImpl(
    private val getPosts: GetPostsUseCase,
    private val scope: CoroutineScope
) : SyncPostsBackgroundTask {

    override suspend fun doWork(): BackgroundTaskResult {
        val postsFlow = callbackFlow {
            getPosts(limit = 20, offset = 0, useCache = false)
                .onError {
                    send(it)
                    close()
                }
                .onSuccess {
                    send(it)
                    close()
                }
                .launchIn(scope)

            awaitClose()
        }

        val response = postsFlow.firstOrNull() ?: return BackgroundTaskResult.FAILURE
        return when (response) {
            is AsyncResult.Error,
            is AsyncResult.Loading -> BackgroundTaskResult.FAILURE
            is AsyncResult.Success -> BackgroundTaskResult.SUCCESS
        }
    }
}