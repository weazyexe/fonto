package dev.weazyexe.fonto.ui.features.feed.preview

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import kotlinx.datetime.Clock
import java.util.UUID

object PostViewStatePreview {
    val default: PostViewState
        @Composable get() = PostViewState(
            id = Post.Id(UUID.randomUUID().toString()),
            title = "Steam перестанет работать на ПК с Windows 7, 8 и 8.1",
            description = "Поддержка прекратится 1 января 2024 года. В Valve рекомендуют обновиться на более свежую версию OC.",
            link = "",
            imageUrl = "https://rozetked.me/images/uploads/webp/Oe98tb9q9Ek5.webp?1679993179",
            publishedAt = Clock.System.now(),
            feed = Feed(
                id = Feed.Id(0),
                title = "Rozetked",
                link = "",
                icon = AppCompatResources.getDrawable(
                    LocalContext.current,
                    DrawableResources.preview_favicon
                )?.toBitmap()?.asLocalImage(),
                type = Feed.Type.RSS,
                category = Category(Category.Id(0), "News")
            ),
            isSaved = false,
            isRead = false
        )

    val noPictures: PostViewState
        @Composable get() = PostViewState(
            id = Post.Id(UUID.randomUUID().toString()),
            title = "Как живет пенсионерка и библиотекарь в Подмосковье с доходом 145 000 ₽",
            description = "«У меня нет особого стимула готовить: дети многое не едят. Сын вообще, как правило, не голоден. Питается чаем, попкорном, пиццей, семечками и прочим. Но ему 26 — воспитывать уже поздно. На завтрак он вообще ест шарики „Несквик“! Карбонара — это подойдет! Жаль, что от таких ужинов я набираю вес. Другие члены семьи стройны необыкновенно»\n" +
                    "\n" +
                    "Героиня нового дневника трат живет с сыном и дочерью, сдает две квартиры и копит на летний отпуск в Турции. Вот как проходит ее неделя:",
            link = "",
            imageUrl = null,
            publishedAt = Clock.System.now(),
            feed = Feed(
                id = Feed.Id(0),
                title = "Rozetked",
                link = "",
                icon = null,
                type = Feed.Type.RSS,
                category = Category(Category.Id(1), "Technologies")
            ),
            isSaved = false,
            isRead = true
        )

    val saved: PostViewState
        @Composable get() = PostViewState(
            id = Post.Id(UUID.randomUUID().toString()),
            title = "Российские права действуют!",
            description = "Когда они водительские, конечно же. Не зря же вы в поте лица наворачивали круги с уставшим от жизни инструктором, чтобы потом не пользоваться водительской корочкой где-нибудь на Кипре или в Хорватии.\n" +
                    "\n" +
                    "Короче, собрали список стран, где вы сможете, имея российские права, управлять бибикой:",
            link = "",
            imageUrl = "https://cdn4.telegram-cdn.org/file/flZogY_p55kA2Xd7RV_UykABn6DzblOJql15NmjTF688nWstLIbVi0EcUmeHZOc_8jHwdXDwNuqgUvXOPCAB5BXa0l79XqhFn_ho5jg1DMcULXNq6IIPIJAFTE_VflgY1A1H8Z9MrKlwEdDRRLz1NDH8kxm_lSD8qD9EOk3EZLr-TFKtzjt7piTNDd9Mf-L9v3e6UNNMi6nlEw4EX7WS1BFFJuB761mTf8G1r-BkzZHdlSVF2XiY8KDQjqH06TPpMICvZZpeKc2q_AlueRqowI86uWrifFdgf-yQOYLp13Q7xq3bhi_fs41nmBh5H_YxXNFZJTQ6FQusLYLzGQZGew.jpg",
            publishedAt = Clock.System.now(),
            feed = Feed(
                id = Feed.Id(0),
                title = "Rozetked",
                link = "",
                icon = AppCompatResources.getDrawable(
                    LocalContext.current,
                    DrawableResources.preview_favicon
                )?.toBitmap()?.asLocalImage(),
                type = Feed.Type.RSS,
                category = Category(Category.Id(2), "Technologies")
            ),
            isSaved = true,
            isRead = true
        )
}
