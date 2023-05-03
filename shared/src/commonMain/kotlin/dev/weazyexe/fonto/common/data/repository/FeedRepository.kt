package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toFeed
import dev.weazyexe.fonto.common.data.mapper.toFeedList
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.flow.first

class FeedRepository(
    private val feedDataSource: FeedDataSource
) {

    suspend fun getAll(): List<Feed> {
        return feedDataSource.getAll().first().toFeedList()
    }

    fun getById(id: Feed.Id): Feed {
        return feedDataSource.getById(id.origin).toFeed()
    }

    fun insert(title: String, link: String, icon: LocalImage?, type: Feed.Type) {
        feedDataSource.insert(title, link, icon?.bytes, type.id)
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
