package io.github.dawidl022.routes

import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRoutes() {
    route("/album") {
        get {
            call.respond(Albums.all())
        }
        get("{id}") {
            Albums.get(call) { it }
        }
        post {
            Albums.add<Album>(call)
        }
        put {
            Albums.put<Album>(call)
        }
        delete("{id}") {
            Albums.delete(call)
        }
    }
}
