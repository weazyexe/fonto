package dev.weazyexe.fonto.common.feature.parser.jsonfeed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonFeedRoot(
    @SerialName("version")
    val version: String,

    @SerialName("title")
    val title: String,

    @SerialName("home_page_url")
    val link: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("items")
    val items: List<JsonFeedItem>
) {

    @Serializable
    data class JsonFeedItem(
        @SerialName("id")
        val id: String,

        @SerialName("title")
        val title: String? = null,

        @SerialName("url")
        val url: String? = null,

        @SerialName("content_text")
        val contentText: String? = null,

        @SerialName("content_html")
        val contentHtml: String? = null,

        @SerialName("summary")
        val summary: String? = null,

        @SerialName("image")
        val image: String? = null,

        @SerialName("banner_image")
        val bannerImage: String? = null,

        @SerialName("date_published")
        val datePublished: String? = null,

        @SerialName("date_modified")
        val dateModified: String? = null,
    )
}