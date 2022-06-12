package io.github.dawidl022.models

import io.github.dawidl022.DATA_DIR
import io.github.dawidl022.models.util.Idable
import io.github.dawidl022.models.util.SeedableTable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.io.File

@Serializable
data class Photo(override val id: Int?, val albumId: Int, val title: String, val url: String, val thumbnailUrl: String)
    : Idable


object Photos : SeedableTable<Photo>("photo") {
    override val id = integer("id").autoIncrement()
    val albumId = integer("album_id")
    val title = varchar("title", 255)
    val url = varchar("url", 1023)
    val thumbnailUrl = varchar("thumbnail_url", 1023)

    @OptIn(ExperimentalSerializationApi::class)
    override fun seed(): List<Photo> =
        Json.decodeFromStream(File(DATA_DIR + "photos.json").inputStream())

    override fun fromRow(row: ResultRow) =
        Photo(
            id = row[id],
            albumId = row[albumId],
            title = row[title],
            url = row[url],
            thumbnailUrl = row[thumbnailUrl],
        )

    override fun <Key : Any> builderSchema(builder: UpdateBuilder<Key>, item: Photo) {
        builder[albumId] = item.albumId
        builder[title] = item.title
        builder[url] = item.url
        builder[thumbnailUrl] = item.thumbnailUrl
    }
}
