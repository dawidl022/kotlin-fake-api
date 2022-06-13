package io.github.dawidl022.plugins

import io.github.dawidl022.graphQLRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        graphQLRoutes()
    }
}
