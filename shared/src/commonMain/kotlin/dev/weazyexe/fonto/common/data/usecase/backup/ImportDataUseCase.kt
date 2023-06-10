package dev.weazyexe.fonto.common.data.usecase.backup

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.usecase.icon.GetFaviconByUrlUseCase
import dev.weazyexe.fonto.common.model.backup.FontoBackupModel
import dev.weazyexe.fonto.common.model.backup.asCategory
import dev.weazyexe.fonto.common.model.backup.asFeed
import dev.weazyexe.fonto.common.model.backup.asPost
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow

class ImportDataUseCase(
    private val feedRepository: FeedRepository,
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
    private val getFaviconByUrl: GetFaviconByUrlUseCase
) {

    suspend operator fun invoke(backup: FontoBackupModel) {
        postRepository.deleteAll()
        feedRepository.deleteAll()
        categoryRepository.deleteAll()

        backup.categories.forEach { categoryBackupModel ->
            categoryRepository.insert(categoryBackupModel.asCategory())
        }

        backup.feeds.asFlow()
        val feedIcons = coroutineScope {
            backup.feeds.map { feedBackupModel ->
               async { feedBackupModel.id to getFaviconByUrl(feedBackupModel.link) }
            }.awaitAll().toMap()
        }

        val categories = categoryRepository.getAll()
        backup.feeds.forEach { feedBackupModel ->
            feedRepository.insertOrIgnore(
                feedBackupModel.asFeed(
                    category = categories.firstOrNull { it.id == feedBackupModel.category },
                    icon = null // FIXME #44 feedIcons[feedBackupModel.id]
                )
            )
        }

        val feeds = feedRepository.getAll()
        backup.posts.forEach { postBackupModel ->
            postRepository.insertOrUpdate(
                postBackupModel.asPost(feed = feeds.first { it.id == postBackupModel.feedId })
            )
        }
    }
}