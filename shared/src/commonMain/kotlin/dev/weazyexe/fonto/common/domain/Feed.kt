package dev.weazyexe.fonto.common.domain

data class Feed(
    val title: String,
    val link: String,
    val description: String,
    val posts: List<Post>
)