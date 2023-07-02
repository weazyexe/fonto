package dev.weazyexe.fonto.common.app.initializer

import dev.weazyexe.fonto.common.app.background.PlatformWorkManager
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsWorker
import dev.weazyexe.fonto.common.data.isNotEmpty
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn

internal class SyncPostsInitializer(
    private val getFeedsUseCase: GetAllFeedsUseCase,
    private val platformWorkManager: PlatformWorkManager,
    private val scope: CoroutineScope
) : Initializer<Unit> {

    override suspend fun initialize(arguments: Unit) {
        getFeedsUseCase()
            .onSuccess {
                if (it.isNotEmpty()) {
                    platformWorkManager.enqueue(SyncPostsWorker::class)
                }
            }
            .launchIn(scope)
    }
}