package io.github.dawidl022.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.github.dawidl022.models.Albums

fun SchemaBuilder.albumSchema() =
    query("albums") {
        resolver { ->
            Albums.all()
        }
    }
