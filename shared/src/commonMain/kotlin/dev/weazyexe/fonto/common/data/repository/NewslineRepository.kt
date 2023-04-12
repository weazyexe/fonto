package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.parser.RssFeed

class NewslineRepository(private val newslineDataSource: NewslineDataSource) {

    suspend fun getNewsline(url: String): RssFeed {
        return newslineDataSource.getNewsline(url)
    }
}