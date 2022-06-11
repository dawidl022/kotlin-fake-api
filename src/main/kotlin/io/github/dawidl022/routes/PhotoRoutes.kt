package io.github.dawidl022.routes

import io.github.dawidl022.models.Photo
import io.github.dawidl022.models.Photos
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Route.photoRoutes() {
    route("/photo") {
        get {
            call.respond(Photos.all())
        }
        get("{id}") {
            Photos.get(call) { it }
        }
        post {
            Photos.add<Photo>(call)
        }
        delete("{id}") {
            Photos.delete(call)
        }
    }
}
