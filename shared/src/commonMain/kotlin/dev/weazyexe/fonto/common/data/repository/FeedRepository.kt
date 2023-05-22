package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.CategoryDataSource
import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.mapper.toCategory
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toFeed
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.flow.first

class FeedRepository(
    private val feedDataSource: FeedDataSource,
    private val categoryDataSource: CategoryDataSource
) {

    suspend fun getAll(): List<Feed> {
        val categoriesDao = categoryDataSource.getAll().first() + null
        val feedsDao = feedDataSource.getAll().first()

        return categoriesDao.flatMap { category ->
            feedsDao
                .filter { category?.id == it.categoryId }
                .map { it.toFeed(category?.toCategory()) }
        }
    }

    fun getById(id: Feed.Id): Feed {
        val feedDao = feedDataSource.getById(id.origin)
        val categoryDao = feedDao.categoryId?.let { categoryDataSource.getById(it) }
        return feedDao.toFeed(categoryDao?.toCategory())
    }

    fun insert(title: String, link: String, icon: LocalImage?, type: Feed.Type, categoryId: Category.Id?) {
        feedDataSource.insert(title, link, icon?.bytes, type.id, categoryId?.origin)
    }

    fun insertOrIgnore(feed: Feed) {
        feedDataSource.insertOrIgnore(feed.toDao())
    }

    fun update(feed: Feed) {
        feedDataSource.update(feed.toDao())
    }

    fun delete(id: Feed.Id) {
        feedDataSource.delete(id.origin)
    }

    fun deleteAll() {
        feedDataSource.deleteAll()
    }
}
