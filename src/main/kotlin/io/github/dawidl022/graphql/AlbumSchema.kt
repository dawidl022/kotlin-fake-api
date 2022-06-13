package io.github.dawidl022.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums
import java.rmi.ServerError

class UpdateFailedException(message: String = "Failed to update.") : Exception(message)

class GraphQLError(val message: String)

fun SchemaBuilder.albumSchema() {
    query("albums") {
        resolver { ->
            Albums.all()
        }
    }
    query("album") {
        resolver { id: Int ->
            Albums.get(id)
        }
    }
    query("albumsByUser") {
        resolver { userId: Int ->
            Albums.byUserId(userId)
        }
    }

    mutation("createAlbum") {
        resolver { userId: Int, title: String ->
            val album = Album(null, userId, title)
            Albums.create(album)
        }
    }
    mutation("updateAlbum") {
        resolver { id: Int, userId: Int, title: String ->
            val album = Album(id, userId, title)
            // FIXME could not get error handling to propagate to the graphQL response due to insufficient documentation
            // or simply lack of implementation in KGraphQL
            Albums.update(album)

        }
    }
    mutation("deleteAlbum") {
        resolver { id: Int ->
            Albums.delete(id)
        }
    }
}
