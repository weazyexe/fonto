package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.app.notifications.NotificationAction
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import dev.weazyexe.fonto.common.model.notification.NotificationVisuals
import dev.weazyexe.fonto.common.resources.StringsProvider

internal class CreateNewPostsNotificationVisualsUseCase(
    private val postRepository: PostRepository,
    private val stringsProvider: StringsProvider
) {

    operator fun invoke(id: Notification.Id, meta: NotificationMeta.NewPosts): NotificationVisuals {
        val posts = postRepository.getPostsByIds(meta.posts)
        val feeds = posts.map { it.feed }.distinctBy { it.id }

        val (title, description) = buildTextVisuals(posts, feeds)
        val imageUrl = if (posts.size == 1) {
            posts.first().imageUrl
        } else {
            null
        }
        val actions = listOf(NotificationAction.MARK_AS_READ)

        return NotificationVisuals(id, title, description, imageUrl, actions)
    }

    private fun buildTextVisuals(posts: List<Post>, feeds: List<Feed>): Pair<String, String> {
        val amountOfFeeds = feeds.size
        val amountOfPosts = posts.size

        return when {
            amountOfFeeds == 1 && amountOfPosts == 1 -> {
                val post = posts.first()
                val title = stringsProvider.string(
                    id = "notification_new_posts_new_publication_single_title",
                    post.feed.title
                )
                val description = post.title
                title to description
            }

            else -> {
                val title = buildMultipleTitle(posts.size)
                val description = buildDescription(feeds)
                title to description
            }
        }
    }

    private fun buildMultipleTitle(amountOfPosts: Int): String =
        stringsProvider.plural(
            id = "notification_new_posts_new_publications_title",
            quantity = amountOfPosts,
            amountOfPosts
        )

    private fun buildDescription(feeds: List<Feed>): String =
        when {
            feeds.size == 1 -> stringsProvider.string(
                id = "notification_new_posts_new_publications_single_description",
                feeds.first().title
            )

            feeds.size == 2 -> stringsProvider.string(
                id = "notification_new_posts_new_publications_two_description",
                feeds[0].title,
                feeds[1].title
            )

            feeds.size >= 3 -> {
                stringsProvider.string(
                    id = "notification_new_posts_new_publications_multiple_description",
                    feeds[0].title,
                    feeds[1].title,
                    feeds.size - 2
                )
            }

            else -> {
                stringsProvider.string(
                    id = "notification_new_posts_new_publications_zero_description"
                )
            }
        }
}