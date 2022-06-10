package io.github.dawidl022.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

data class Album(override val id: Int?, val userId: Int, val title: String) : Idable {
    constructor(id: String, userId: String, title: String) : this(id.toInt(), userId.toInt(), title)
}

@JsonRootName("album")
data class AlbumXML(
    @set:JsonProperty("userId")
    var userId: String? = null,

    @set:JsonProperty("id")
    var id: String? = null,

    @set:JsonProperty("title")
    var title: String? = null
)

@JsonRootName("albums")
data class AlbumsXML(
    @set:JsonProperty("album") var albums: MutableList<AlbumXML> = mutableListOf()
) : XMLParsable<MutableList<Album>> {
    override val data
        get() =
            albums.map { Album(it.id ?: "", it.userId ?: "", it.title ?: "") }.toMutableList()
}

object Albums : InMemoryResource<Album>() {
    override val storage: MutableList<Album> by lazy {
        XMLParser.parse(
            "albums.xml",
            AlbumsXML::class.java
        )
    }
}
