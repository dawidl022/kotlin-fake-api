package io.github.dawidl022.models.util

import io.github.dawidl022.routes.noIdResponse
import io.github.dawidl022.routes.notFoundResponse
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

interface Idable {
    val id: Int?
}

abstract class InMemoryResource<T : Idable>(val name: String) {
    abstract val storage: MutableList<T>

    suspend fun get(call: ApplicationCall, response: (T) -> Any) {
        val id = call.parameters["id"] ?: return noIdResponse(call)
        val resource = storage.find { it.id.toString() == id } ?:
        return notFoundResponse(call, name, id)
        call.respond(response(resource))
    }

    suspend inline fun <reified E : T> add(call: ApplicationCall) {
        val item = call.receive<E>()
        storage.add(item)
        call.respondText(
            "Added $name.",
            status = HttpStatusCode.Accepted
        )
    }

    suspend fun delete(call: ApplicationCall) {
        get(call) {
            storage.remove(it)
            TextContent(
                "${name.replaceFirstChar(Char::uppercase)} removed.",
                status = HttpStatusCode.Accepted,
                contentType = ContentType.Text.Plain
            )
        }
    }
}
