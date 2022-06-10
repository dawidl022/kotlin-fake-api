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
            call.respond(Photos.storage)
        }
        get("{id}") {
            Photos.get(call) { it }
        }
        post {
            val photo = call.receive<Photo>()
            Photos.storage.add(photo)
            call.respondText("Photo added.", status = HttpStatusCode.Accepted)
        }
        delete("{id}") {
            Photos.get(call) {
                Photos.storage.remove(it)
                TextContent(
                    "Photo removed.",
                    status = HttpStatusCode.Accepted,
                    contentType = ContentType.Text.Plain
                )
            }
        }
    }
}