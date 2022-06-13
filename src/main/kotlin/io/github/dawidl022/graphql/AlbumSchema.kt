package io.github.dawidl022.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.github.dawidl022.models.Albums

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
        resolver{ userId: Int ->
            Albums.byUserId(userId)
        }
    }
}
