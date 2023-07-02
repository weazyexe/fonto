package dev.weazyexe.fonto.common.feature.debug

import dev.weazyexe.fonto.common.model.feed.Feed

internal val VALID_FEED = listOf(
    Feed(
        id = Feed.Id(1),
        title = "TechCrunch",
        link = "https://techcrunch.com/feed/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(2),
        title = "Habr",
        link = "https://habr.ru/rss/all/all?fl=ru",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(3),
        title = "Mashable",
        link = "https://mashable.com/feed/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(4),
        title = "Engadget",
        link = "https://www.engadget.com/rss.xml",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(5),
        title = "Gizmodo",
        link = "https://gizmodo.com/rss",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(6),
        title = "Ars Technica",
        link = "https://arstechnica.com/feed/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(7),
        title = "Wired",
        link = "https://www.wired.com/feed/rss",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(8),
        title = "DTF",
        link = "https://dtf.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(9),
        title = "The Guardian",
        link = "https://www.theguardian.com/world/rss",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(10),
        title = "VC.ru",
        link = "https://vc.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
)

internal val PARTIALLY_INVALID_FEED = listOf(
    Feed(
        id = Feed.Id(1),
        title = "TechCrunch",
        link = "https://techcrunch.com/feed/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(2),
        title = "Habr",
        link = "https://habr.ru/rss/all/all?fl=ru",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(3),
        title = "Mashable",
        link = "https://mashable.com/feed/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(4),
        title = "Engadget",
        link = "https://www.engadget.com/rss.xml",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(5),
        title = "Gizmodo",
        link = "https://gizmodo.com/rss",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(6),
        title = "Google",
        link = "https://www.google.com/",
        icon = null,
        type = Feed.Type.RSS,
        null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(7),
        title = "Wired",
        link = "https://www.wired.com/feed/rss",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(8),
        title = "DTF",
        link = "https://dtf.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(9),
        title = "TJournal (press F)",
        link = "https://tjournal.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(10),
        title = "VC.ru",
        link = "https://vc.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
)

internal val FULLY_INVALID_FEED = listOf(
    Feed(
        id = Feed.Id(1),
        title = "ya.ru",
        link = "https://ya.ru",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(2),
        title = "Google",
        link = "https://www.google.com/",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    ),
    Feed(
        id = Feed.Id(3),
        title = "TJournal (press F)",
        link = "https://tjournal.ru/rss/all",
        icon = null,
        type = Feed.Type.RSS,
        category = null,
        areNotificationsEnabled = false
    )
)
