package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.CategoryDataSource
import dev.weazyexe.fonto.common.data.mapper.toCategory
import dev.weazyexe.fonto.common.data.mapper.toCategoryList
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.coroutines.flow.first

class CategoryRepository(
    private val categoryDataSource: CategoryDataSource
) {

    suspend fun getAll(): List<Category> {
        return categoryDataSource.getAll().first().toCategoryList()
    }

    fun getById(id: Category.Id): Category {
        return categoryDataSource.getById(id.origin).toCategory()
    }

    fun insert(title: String) {
        categoryDataSource.insert(title)
    }

    fun update(category: Category) {
        categoryDataSource.update(category.toDao())
    }

    fun delete(id: Category.Id) {
        categoryDataSource.delete(id.origin)
    }

    fun deleteAll() {
        categoryDataSource.deleteAll()
    }
}