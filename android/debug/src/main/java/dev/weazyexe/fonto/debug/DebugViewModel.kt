package dev.weazyexe.fonto.debug

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import kotlinx.coroutines.launch

class DebugViewModel(
    private val deleteAllFeeds: DeleteAllFeedsUseCase,
    private val createFeed: CreateFeedUseCase
) : CoreViewModel<DebugState, DebugEffect>() {

    override val initialState: DebugState = DebugState()

    private val feeds = listOf(
        Feed(
            id = 1,
            title = "TechCrunch",
            link = "https://techcrunch.com/feed/",
            icon = null
        ),
        Feed(
            id = 2,
            title = "Habr",
            link = "https://habr.ru/rss/all/all?fl=ru",
            icon = null
        ),
        Feed(
            id = 3,
            title = "Mashable",
            link = "https://mashable.com/feed/",
            icon = null
        ),
        Feed(
            id = 4,
            title = "Engadget",
            link = "https://www.engadget.com/rss.xml",
            icon = null
        ),
        Feed(
            id = 5,
            title = "Gizmodo",
            link = "https://gizmodo.com/rss",
            icon = null
        ),
        Feed(
            id = 6,
            title = "Ars Technica",
            link = "https://arstechnica.com/feed/",
            icon = null
        ),
        Feed(
            id = 7,
            title = "Wired",
            link = "https://www.wired.com/feed/rss",
            icon = null
        ),
        Feed(
            id = 8,
            title = "DTF",
            link = "https://dtf.ru/rss/all",
            icon = null
        ),
        Feed(
            id = 9,
            title = "The Guardian",
            link = "https://www.theguardian.com/world/rss",
            icon = null
        ),
        Feed(
            id = 10,
            title = "VC.ru",
            link = "https://vc.ru/rss/all",
            icon = null
        ),
    )

    fun addMockFeeds() = viewModelScope.launch {
        deleteAllFeeds()
        feeds.forEach {
            createFeed(it.title, it.link, it.icon)
        }
    }
}