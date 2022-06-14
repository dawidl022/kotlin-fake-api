package io.github.dawidl022.server

import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.types.GraphQLRequest
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import io.ktor.server.request.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.IOException


/**
 * Workaround for this bug:
 * `kotlinx.serialization.json.internal.JsonDecodingException:
 * Unexpected JSON token at offset 0: Expected beginning of the string, but got {`
 *
 * Receives same parameters as [GraphQLRequest] so that [io.ktor.server.request.ApplicationRequest] can deserialise
 * directly into an object, as it seems that deserialising into a string fails.
 */
@Serializable
data class GraphQLRequestFacade(
    val query: String,
    val operationName: String? = null,
    val variables: Map<String, @Contextual Any?>? = null
) {
    val realRequest
        get() = GraphQLRequest(query, operationName, variables)
}

/**
 * Custom logic for how Ktor parses the incoming [ApplicationRequest] into the [GraphQLServerRequest]
 */
class KtorGraphQLRequestParser(
) : GraphQLRequestParser<ApplicationRequest> {

    override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest = try {
        // modified from example to use facade object rather than a string and mapper
        request.call.receive<GraphQLRequestFacade>().realRequest
    } catch (e: ContentTransformationException) {
        throw IOException("Unable to parse GraphQL payload.")
    }
}
