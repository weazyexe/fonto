package dev.weazyexe.fonto.common.data.usecase.backup

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.backup.FontoBackupModel
import dev.weazyexe.fonto.common.model.backup.JsonString
import dev.weazyexe.fonto.common.model.backup.asBackupModel
import kotlinx.serialization.json.Json

class GetExportDataUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend operator fun invoke(): JsonString {
        val feeds = feedRepository.getAll()
        val posts = postRepository.getAll()
        val categories = categoryRepository.getAll()

        return FontoBackupModel(
            feeds = feeds.map { it.asBackupModel() },
            posts = posts.map { it.asBackupModel() },
            categories = categories.map { it.asBackupModel() }
        ).let { json.encodeToString(FontoBackupModel.serializer(), it) }
    }
}