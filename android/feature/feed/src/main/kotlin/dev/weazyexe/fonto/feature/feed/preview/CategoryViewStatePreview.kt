package dev.weazyexe.fonto.feature.feed.preview

import androidx.compose.runtime.Composable
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.feature.feed.components.category.CategoryViewState

object CategoryViewStatePreview {

    val default: CategoryViewState
        @Composable
        get() = CategoryViewState(
            id = Category.Id(0),
            title = "News",
            amountOfFeeds = 4
        )

    val noFeeds: CategoryViewState
        @Composable
        get() = CategoryViewState(
            id = Category.Id(1),
            title = "Technologies",
            amountOfFeeds = 0
        )
}
