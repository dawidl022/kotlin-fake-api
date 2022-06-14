package io.github.dawidl022.resolvers

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema

val schema = toSchema(
    config = SchemaGeneratorConfig(supportedPackages = listOf("io.github.dawidl022.models")),
    queries = listOf(
        AlbumQuery(),
        PhotoQuery(),
        TodoQuery(),
    ).map(::TopLevelObject),
    mutations = listOf(
        AlbumMutation(),
        PhotoMutation(),
        TodoMutation(),
    ).map(::TopLevelObject)
)
