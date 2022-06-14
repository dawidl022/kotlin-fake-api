package io.github.dawidl022.routes

import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.execution.GraphQLServer
import io.github.dawidl022.server.KtorServer
import io.github.dawidl022.server.getGraphQLServer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.graphQLRoutes() {
    val graphqlServer = KtorServer()

    route("/graphql") {
        post {
            graphqlServer.handle(call)
        }
    }
}
