package dev.weazyexe.fonto.common.model.feed

@JvmInline
value class Posts(val posts: List<Post>) : List<Post> by posts {

    companion object {
        val EMPTY = Posts(emptyList())
    }
}
