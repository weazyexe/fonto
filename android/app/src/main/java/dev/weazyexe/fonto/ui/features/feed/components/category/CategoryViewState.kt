package dev.weazyexe.fonto.ui.features.feed.components.category

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Category

@Immutable
data class CategoryViewState(
    val id: Category.Id,
    val title: String,
    val amountOfFeeds: Int
)

fun Category.asViewState(amountOfFeeds: Int): CategoryViewState = CategoryViewState(
    id = id,
    title = title,
    amountOfFeeds = amountOfFeeds
)

fun CategoryViewState.asCategory(): Category =
    Category(
        id = id,
        title = title
    )