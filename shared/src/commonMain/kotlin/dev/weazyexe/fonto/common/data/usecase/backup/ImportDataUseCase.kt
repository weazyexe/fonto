package dev.weazyexe.fonto.common.data.usecase.backup

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.usecase.icon.GetFaviconByUrlUseCase
import dev.weazyexe.fonto.common.feature.backup.FileReader
import dev.weazyexe.fonto.common.model.backup.asCategory
import dev.weazyexe.fonto.common.model.backup.asFeed
import dev.weazyexe.fonto.common.model.backup.asPost
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull

internal class ImportDataUseCase(
    private val feedRepository: FeedRepository,
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
    private val getFaviconByUrl: GetFaviconByUrlUseCase,
    private val parseBackupData: ParseBackupDataUseCase
) {

    operator fun invoke(reader: FileReader): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())

        val backup = parseBackupData(reader)

        postRepository.deleteAll()
        feedRepository.deleteAll()
        categoryRepository.deleteAll()

        backup.categories.forEach { categoryBackupModel ->
            categoryRepository.insert(categoryBackupModel.asCategory())
        }

        val feedIcons = coroutineScope {
            backup.feeds.map { feedBackupModel ->
                async { feedBackupModel.id to getFaviconByUrl(feedBackupModel.link).getValue() }
            }.awaitAll().toMap()
        }

        val categories = categoryRepository.getAll()
        backup.feeds.forEach { feedBackupModel ->
            feedRepository.insertOrIgnore(
                feedBackupModel.asFeed(
                    category = categories.firstOrNull { it.id == feedBackupModel.category },
                    icon = feedIcons[feedBackupModel.id]
                )
            )
        }

        val feeds = feedRepository.getAll()
        backup.posts.forEach { postBackupModel ->
            postRepository.insertOrUpdate(
                postBackupModel.asPost(feed = feeds.first { it.id == postBackupModel.feedId })
            )
        }

        emit(AsyncResult.Success(Unit))
    }

    private suspend fun <T> Flow<AsyncResult<T>>.getValue(): T? =
        filterIsInstance<AsyncResult.Success<T>>().firstOrNull()?.data
}