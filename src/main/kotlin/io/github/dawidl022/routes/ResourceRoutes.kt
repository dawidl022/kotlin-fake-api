package io.github.dawidl022.routes

import io.github.dawidl022.handlers.ResourceHandler
import io.github.dawidl022.models.Photo
import io.github.dawidl022.models.util.Idable
import io.ktor.server.application.*
import io.ktor.server.routing.*

inline fun <reified T: Idable> Route.resourceRoutes(basePath: String, handler: ResourceHandler<T>) {
    route(basePath) {
        get {
            handler.all(call)
        }
        get("{id}") {
            handler.get(call)
        }
        post {
            handler.add<T>(call)
        }
        put("{id}") {
            handler.put<T>(call)
        }
        delete("{id}") {
            handler.delete(call)
        }
    }
}
