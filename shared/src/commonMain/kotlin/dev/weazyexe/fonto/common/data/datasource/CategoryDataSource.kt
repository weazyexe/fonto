package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.CategoryDao
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

internal class CategoryDataSource(database: FontoDatabase) {

    private val queries = database.categoryDaoQueries

    fun getAll(): Flow<List<CategoryDao>> = queries.getAll().flowList()

    fun getById(id: Long): CategoryDao = queries.getById(id).executeAsOne()

    fun getByIdOrNull(id: Long): CategoryDao? = queries.getById(id).executeAsOneOrNull()

    fun insert(title: String) = queries.insert(title)

    fun insert(category: CategoryDao) = queries.insertWholeCategory(category)

    fun update(category: CategoryDao) = queries.update(category)

    fun delete(id: Long) = queries.delete(id)

    fun deleteAll() = queries.deleteAll()
}