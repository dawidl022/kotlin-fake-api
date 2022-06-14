package io.github.dawidl022.resolvers

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema

val schema = toSchema(
    config = SchemaGeneratorConfig(supportedPackages = listOf("io.github.dawidl022.models")),
    queries = listOf(
        AlbumQuery()
    ).map(::TopLevelObject)
)
