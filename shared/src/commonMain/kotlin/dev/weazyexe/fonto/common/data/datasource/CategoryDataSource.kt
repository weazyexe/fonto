package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.CategoryDao
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

class CategoryDataSource(database: FontoDatabase) {

    private val queries = database.categoryDaoQueries

    fun getAll(): Flow<List<CategoryDao>> = queries.getAll().flowList()

    fun getById(id: Long): CategoryDao = queries.getById(id).executeAsOne()

    fun insert(title: String) = queries.insert(title)

    fun update(category: CategoryDao) = queries.update(category)

    fun delete(id: Long) = queries.delete(id)

    fun deleteAll() = queries.deleteAll()
}