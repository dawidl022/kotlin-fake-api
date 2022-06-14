/*
 * Copyright 2022 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.dawidl022.server

import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.types.GraphQLRequest
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.databind.ObjectMapper
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

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest = try {
        // modified from example to use facade object rather than a string and mapper
        val rawRequest = request.call.receive<GraphQLRequestFacade>()
        rawRequest.realRequest
    } catch (e: IOException) {
        throw IOException("Unable to parse GraphQL payload.")
    }
}
