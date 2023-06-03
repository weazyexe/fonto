package dev.weazyexe.fonto.common.model.backup

data class ExportStrategy(
    val categories: Boolean,
    val feeds: Boolean,
    val posts: Boolean
) {

    val isFeedExportAvailable: Boolean
        get() = categories

    val isPostsExportAvailable: Boolean
        get() = categories && feeds
}