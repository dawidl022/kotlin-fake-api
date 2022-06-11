package io.github.dawidl022.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import io.github.dawidl022.models.util.Idable
import io.github.dawidl022.models.util.InMemoryResource
import io.github.dawidl022.models.util.XMLParsable
import io.github.dawidl022.models.util.XMLParser
import kotlinx.serialization.Serializable

@Serializable
data class Album(override val id: Int?, val userId: Int, val title: String) : Idable {
    constructor(id: String, userId: String, title: String) : this(id.toInt(), userId.toInt(), title)
}

object Albums : InMemoryResource<Album>("album") {
    override val storage: MutableList<Album> by lazy {
        XMLParser.parse("albums.xml", AlbumsXML::class.java).toMutableList()
    }
}

@JsonRootName("album")
private data class AlbumXML(
    @set:JsonProperty("userId")
    var userId: String? = null,

    @set:JsonProperty("id")
    var id: String? = null,

    @set:JsonProperty("title")
    var title: String? = null
)

@JsonRootName("albums")
private data class AlbumsXML(
    @set:JsonProperty("album") var albums: List<AlbumXML> = mutableListOf()
) : XMLParsable<List<Album>> {
    override val data
        get() =
            albums.map { Album(it.id ?: "", it.userId ?: "", it.title ?: "") }
}
