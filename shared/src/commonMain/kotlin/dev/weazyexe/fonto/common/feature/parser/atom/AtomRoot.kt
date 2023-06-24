package dev.weazyexe.fonto.common.feature.parser.atom

import dev.weazyexe.fonto.common.feature.parser.ImageMimeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName(value = "feed", namespace = "http://www.w3.org/2005/Atom")
internal data class AtomRoot(

    @XmlElement(true)
    @SerialName("title")
    val title: String,

    @XmlElement(true)
    val link: AtomLink?,

    @XmlElement(true)
    val entries: List<AtomEntry>?
) {

    @Serializable
    @SerialName("link")
    data class AtomLink(
        @XmlElement(false)
        @SerialName("rel")
        val rel: String?,

        @XmlElement(false)
        @SerialName("type")
        val type: String?,

        @XmlElement(false)
        @SerialName("href")
        val href: String
    )

    @Serializable
    @SerialName("entry")
    data class AtomEntry(
        @XmlElement(true)
        @SerialName("id")
        val id: String,

        @XmlElement(true)
        @SerialName("title")
        val title: String,

        @XmlElement(true)
        @SerialName("updated")
        val updated: String,

        @XmlElement(true)
        @SerialName("published")
        val published: String?,

        @XmlElement(true)
        val link: AtomLink,

        @XmlElement(true)
        @SerialName("summary")
        val summary: String?,

        @XmlElement(true)
        @SerialName("content")
        val content: String?,

        @XmlElement(true)
        @XmlSerialName(value = "thumbnail", prefix = "media")
        val mediaThumbnail: MediaThumbnail?
    ) {

        val imageUrl: String?
            get() {
                val imageMimeTypes = ImageMimeType.values().map { it.value }

                if (mediaThumbnail != null) return mediaThumbnail.url
                if (link?.rel == "enclosure" && link.type in imageMimeTypes) return link.href

                return null
            }

        @Serializable
        data class MediaThumbnail(
            @XmlElement(false)
            @SerialName("url")
            val url: String
        )
    }
}