package io.github.dawidl022.models

import io.github.dawidl022.routes.noIdResponse
import io.github.dawidl022.routes.notFoundResponse
import io.ktor.server.application.*
import io.ktor.server.response.*

interface Idable {
    val id: Int?
}

abstract class InMemoryResource<T : Idable> {
    abstract val storage: MutableList<T>

    suspend fun get(call: ApplicationCall, response: (T) -> Any) {
        val id = call.parameters["id"] ?: return noIdResponse(call)
        val resource = storage.find { it.id.toString() == id } ?:
        return notFoundResponse(call, "photo", id)
        call.respond(response(resource))
    }
}