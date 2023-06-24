package dev.weazyexe.fonto.common.feature.parser.rss

import dev.weazyexe.fonto.common.feature.parser.ImageMimeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("rss")
internal data class RssRoot(
    @XmlElement(true)
    val channel: RssChannel,
    @XmlElement(false)
    val version: String
) {

    @Serializable
    @SerialName("channel")
    data class RssChannel(
        @XmlElement(true)
        @SerialName("title")
        val title: String,

        @XmlElement(true)
        @SerialName("link")
        val link: String,

        @XmlElement(true)
        @SerialName("description")
        val description: String,

        @XmlElement(true)
        val items: List<RssItem>?
    ) {

        @Serializable
        @SerialName("item")
        data class RssItem(
            @XmlElement(true)
            @SerialName("title")
            val title: String?,

            @XmlElement(true)
            @SerialName("link")
            val link: String?,

            @XmlElement(true)
            @SerialName("description")
            val description: String?,

            @XmlElement(true)
            @SerialName("pubDate")
            val pubDate: String?,

            @XmlElement(true)
            val enclosure: List<RssEnclosure>?
        ) {

            val imageUrl: String?
                get() {
                    val imageMimeTypes = ImageMimeType.values().map { it.value }
                    val enc = enclosure?.firstOrNull { it.type in imageMimeTypes } ?: return null
                    return enc.url
                }

            @Serializable
            @SerialName("enclosure")
            data class RssEnclosure(
                @XmlElement(true)
                @SerialName("url")
                val url: String?,

                @XmlElement(true)
                @SerialName("type")
                val type: String?,
            )
        }
    }
}