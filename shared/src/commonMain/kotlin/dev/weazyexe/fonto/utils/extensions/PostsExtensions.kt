package dev.weazyexe.fonto.utils.extensions

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.feed.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun AsyncResult<Posts>.update(post: Post): AsyncResult<Posts> {
    return withContext(Dispatchers.Default) {
        map { posts ->
            Posts(
                posts = posts.map { if (it.id == post.id) post else it }
            )
        }
    }
}

fun AsyncResult<Posts>.firstOrNull(predicate: (Post) -> Boolean): Post? {
    val postsResult = this as? AsyncResult.Success ?: return null
    return postsResult.data.posts.firstOrNull { predicate(it) }
}