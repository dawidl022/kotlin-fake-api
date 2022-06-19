package io.github.dawidl022.routes

import io.github.dawidl022.handlers.ResourceHandler
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
    val handler = ResourceHandler(Photos)
    resourceRoutes("/photo", handler)
}
