package dev.weazyexe.fonto.common.app.background.sync

import dev.weazyexe.fonto.common.app.background.WorkerResult
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostsUseCase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn

internal class SyncPostsWorkerImpl(
    private val getPosts: GetPostsUseCase
) : SyncPostsWorker {

    override suspend fun doWork(): WorkerResult {
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
                .launchIn(this)

            awaitClose()
        }

        val response = postsFlow.firstOrNull() ?: return WorkerResult.FAILURE
        return when (response) {
            is AsyncResult.Error,
            is AsyncResult.Loading -> WorkerResult.FAILURE

            is AsyncResult.Success -> WorkerResult.SUCCESS
        }
    }
}