package dev.weazyexe.fonto.common.model.notification

import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface NotificationMeta {

    @Serializable
    data class NewPosts(val posts: List<Post.Id>) : NotificationMeta
}

internal inline fun <reified M : NotificationMeta> M.encode(json: Json): String {
    return json.encodeToString(
        serializer = serializer(),
        value = this
    )
}

internal inline fun <reified M : NotificationMeta> String.decode(json: Json): M {
    return json.decodeFromString(
        deserializer = serializer(),
        string = this
    )
}