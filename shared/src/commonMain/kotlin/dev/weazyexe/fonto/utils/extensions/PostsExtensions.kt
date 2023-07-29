package dev.weazyexe.fonto.utils.extensions

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun AsyncResult<List<Post>>.update(post: Post): AsyncResult<List<Post>> {
    return withContext(Dispatchers.Default) {
        map { posts ->
            posts.map { if (it.id == post.id) post else it }
        }
    }
}

fun AsyncResult<List<Post>>.firstOrNull(predicate: (Post) -> Boolean): Post? {
    val postsResult = this as? AsyncResult.Success ?: return null
    return postsResult.data.firstOrNull { predicate(it) }
}