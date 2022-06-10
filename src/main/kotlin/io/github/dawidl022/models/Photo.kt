package io.github.dawidl022.models

import io.github.dawidl022.DATA_DIR
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@Serializable
data class Photo(override val id: Int?, val albumId: Int, val title: String, val url: String, val thumbnailUrl: String)
    : Idable


object Photos : InMemoryResource<Photo>() {
    @OptIn(ExperimentalSerializationApi::class)
    override val storage: MutableList<Photo> by lazy {
        Json.decodeFromStream(File(DATA_DIR + "photos.json").inputStream())
    }
}
