package dev.weazyexe.fonto.common.feature.posts

import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.filter.Filter
import dev.weazyexe.fonto.common.feature.filter.Multiple
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.serialization.Serializable

sealed interface PostsFilter : Filter

data class BySaved(
    override val isEnabled: Boolean
) : Bool<BySaved>, PostsFilter {

    override fun toggle(): BySaved {
        return BySaved(!isEnabled)
    }
}

data class ByRead(
    override val isEnabled: Boolean
) : Bool<ByRead>, PostsFilter {

    override fun toggle(): ByRead {
        return ByRead(!isEnabled)
    }
}

data class ByPostDates(
    override val range: Dates.Range?
) : Dates<ByPostDates>, PostsFilter {

    override fun change(range: Dates.Range?): ByPostDates {
        return ByPostDates(range)
    }
}

data class ByFeed(
    override val values: List<Data>,
    override val possibleValues: List<Data>
) : Multiple<ByFeed.Data, ByFeed>, PostsFilter {

    override fun change(newValue: List<Data>, newPossibleValues: List<Data>): ByFeed {
        return ByFeed(newValue, newPossibleValues)
    }

    @Serializable
    data class Data(
        val id: Feed.Id,
        val title: String
    )
}

data class ByCategory(
    override val values: List<Category>,
    override val possibleValues: List<Category>
) : Multiple<Category, ByCategory>, PostsFilter {

    override fun change(newValue: List<Category>, newPossibleValues: List<Category>): ByCategory {
        return ByCategory(newValue, newPossibleValues)
    }
}

internal val PostsFilters = listOf(
    BySaved(isEnabled = false),
    ByRead(isEnabled = false),
    ByPostDates(range = null),
    ByFeed(values = emptyList(), possibleValues = emptyList()),
    ByCategory(values = emptyList(), possibleValues = emptyList())
)