package io.github.dawidl022.routes

import io.github.dawidl022.handlers.ResourceHandler
import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRoutes() {
    val handler = ResourceHandler(Albums)
    resourceRoutes("/album", handler)
}
