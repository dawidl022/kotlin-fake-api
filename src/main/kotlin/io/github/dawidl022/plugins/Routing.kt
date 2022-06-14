package io.github.dawidl022.plugins

import io.github.dawidl022.routes.graphQLRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        graphQLRoutes()
    }
}
