package io.github.dawidl022.routes

import io.github.dawidl022.models.Todo
import io.github.dawidl022.models.Todos
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.todoRoutes() {
    route("/todo") {
        get {
            call.respond(Todos.all())
        }
        get ("{id}") {
            Todos.get(call) { it }
        }
        post {
            Todos.add<Todo>(call)
        }
        put {
            Todos.put<Todo>(call)
        }
        delete("{id}") {
            Todos.delete(call)
        }
    }
}
