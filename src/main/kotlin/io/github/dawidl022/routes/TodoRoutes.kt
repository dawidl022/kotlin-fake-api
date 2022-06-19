package io.github.dawidl022.routes

import io.github.dawidl022.handlers.ResourceHandler
import io.github.dawidl022.models.Todo
import io.github.dawidl022.models.Todos
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.todoRoutes() {
    val handler = ResourceHandler(Todos)
    resourceRoutes("/todo", handler)
}
