package io.github.dawidl022.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun noIdResponse(call: ApplicationCall) = call.respondText(
    "Missing id",
    status = HttpStatusCode.BadRequest
)

suspend fun notFoundResponse(call: ApplicationCall, resourceName: String, id: String) {
    call.respondText(
        "No $resourceName with id $id.",
        status = HttpStatusCode.NotFound
    )
}

