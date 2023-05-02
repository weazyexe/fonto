package dev.weazyexe.fonto.common.parser.jsonfeed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonFeedPost(
    @SerialName("id")
    val id: String,

    @SerialName("url")
    val url: String?,

    @SerialName("external_url")
    val externalUrl: String?,

    @SerialName("title")
    val title: String?,

    @SerialName("summary")
    val summary: String?,

    @SerialName("content_text")
    val contentText: String?,

    @SerialName("image")
    val imageUrl: String?,

    @SerialName("banner_image")
    val bannerImage: String?,

    @SerialName("date_published")
    val datePublished: String?,

    @SerialName("date_modified")
    val dateModified: String?
)