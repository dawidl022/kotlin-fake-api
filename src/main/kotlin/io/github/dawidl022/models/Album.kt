package io.github.dawidl022.models

import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import io.github.dawidl022.models.util.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class Album(override val id: Int? = null, val userId: Int, val title: String) : Idable {
    constructor(id: String, userId: String, title: String) : this(id.toInt(), userId.toInt(), title)
}

object Albums : SeedableTable<Album>("album") {
    override val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val title = varchar("title", 255)

    fun byUserId(userId: Int) =
        transaction {
            select {
                this@Albums.userId eq userId
            }.map(::fromRow)
        }

    override val primaryKey = PrimaryKey(id)

    override fun seed(): List<Album> =
        XMLParser.parse("albums.xml", AlbumsXML::class.java)

    override fun fromRow(row: ResultRow) =
        Album(
            id = row[id],
            userId = row[userId],
            title = row[title],
        )

    override fun <Key : Any> builderSchema(builder: UpdateBuilder<Key>, item: Album) {
        builder[userId] = item.userId
        builder[title] = item.title
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
    @set:JsonProperty("album") var albums: List<AlbumXML> = listOf()
) : XMLParsable<List<Album>> {
    override val data
        get() =
            albums.map { Album(it.id ?: "", it.userId ?: "", it.title ?: "") }
}
