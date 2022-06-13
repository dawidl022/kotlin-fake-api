package io.github.dawidl022

import com.apurebase.kgraphql.KGraphQL
import io.github.dawidl022.graphql.albumSchema
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GraphQLRequest(val query: String)

fun Route.graphQLRoutes() {
    val schema = KGraphQL.schema {
        albumSchema()
    }
    route("/graphql") {
        post {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respondText(schema.execute(graphRequest.query), contentType = ContentType.Application.Json)
        }
    }
}
