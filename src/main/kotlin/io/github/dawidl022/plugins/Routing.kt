package io.github.dawidl022.plugins

import io.github.dawidl022.routes.photoRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        photoRoutes()
    }
}
