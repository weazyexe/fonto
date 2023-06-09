package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.CategoryDao
import dev.weazyexe.fonto.common.model.feed.Category

internal fun List<CategoryDao>.toCategoryList(): List<Category> = map { it.toCategory() }

internal fun CategoryDao.toCategory(): Category =
    Category(
        id = Category.Id(id),
        title = title
    )

internal fun Category.toDao(): CategoryDao =
    CategoryDao(
        id = id.origin,
        title = title
    )