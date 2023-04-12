package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.model.base.LocalImage

class IconRepository(private val iconDataSource: IconDataSource) {

    suspend fun loadIcon(url: String): LocalImage {
        val response = iconDataSource.loadIcon(url)
        return LocalImage(response)
    }
}