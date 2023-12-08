package dev.weazyexe.fonto.android.feature.feed.preview

import androidx.compose.runtime.Composable
import dev.weazyexe.fonto.android.feature.feed.components.category.CategoryViewState
import dev.weazyexe.fonto.common.model.feed.Category

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
