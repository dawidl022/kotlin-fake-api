package io.github.dawidl022.handlers

import io.github.dawidl022.models.util.Idable
import io.github.dawidl022.models.util.SeedableTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ResourceHandler<T : Idable>(val table: SeedableTable<T>) {
    suspend fun all(call: ApplicationCall) {
        // as Any needed to bypass generics limitation
        call.respond(table.all() as Any)
    }

    suspend fun get(call: ApplicationCall) {
        val queryId = getQueryId(call) ?: return
        val resource = table.get(queryId) ?: return notFoundResponse(call, queryId)
        call.respond(resource as Any)
    }

    suspend inline fun <reified E : T> add(call: ApplicationCall) {
        val item = call.receive<E>()
        if (table.add(item))
            call.respondText(
                "Added ${table.name}",
                status = HttpStatusCode.Created
            )
        else
            call.respondText(
                "Failed to add ${table.name}",
                status = HttpStatusCode.InternalServerError
            )
    }

    suspend fun delete(call: ApplicationCall) {
        val queryId = getQueryId(call) ?: return
        table.get(queryId) ?: return notFoundResponse(call, queryId)

        if (table.delete(queryId))
            call.respondText(
                "${table.name.replaceFirstChar(Char::uppercase)} removed.",
                status = HttpStatusCode.OK,
            )
        else
            notFoundResponse(call, queryId)
    }

    suspend inline fun <reified E : T> put(call: ApplicationCall) {
        val queryId = getQueryId(call) ?: return
        if (table.get(queryId) == null) {
            return notFoundResponse(call, queryId)
        }

        val item = call.receive<E>()
        if (!table.put(queryId, item))
            call.respondText(
                "Failed to update ${table.name} with id $queryId",
                status = HttpStatusCode.InternalServerError
            )
        else
            call.respondText(
                "Updated ${table.name} with id $queryId",
                status = HttpStatusCode.Created
            )
    }

    // Private functions needed to be made public for inline functions to work
    suspend fun getQueryId(call: ApplicationCall): Int? {
        val id = call.parameters["id"].apply { if (this == null) noIdResponse(call) }
        return id?.toIntOrNull().apply { if (this == null) invalidIdResponse(call) }
    }

    suspend fun notFoundResponse(call: ApplicationCall, id: Int) {
        call.respondText(
            "No ${table.name} with id $id.",
            status = HttpStatusCode.NotFound
        )
    }

    private suspend fun invalidIdResponse(call: ApplicationCall) {
        call.respondText(
            "${table.name.replaceFirstChar(Char::uppercase)} id must be an integer",
            status = HttpStatusCode.BadRequest
        )
    }

    private suspend fun noIdResponse(call: ApplicationCall) = call.respondText(
        "Missing id",
        status = HttpStatusCode.BadRequest
    )
}
