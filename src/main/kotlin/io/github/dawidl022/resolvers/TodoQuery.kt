package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Todo
import io.github.dawidl022.models.Todos
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TodoQuery {
    fun todos(): List<Todo> =
        Todos.all()

    fun todo(id: Int): Todo =
        Todos.get(id)

    fun todosByUser(userId: Int): List<Todo> =
        transaction {
            Todos.select {
                Todos.userId eq userId
            }.map(Todos::fromRow)
        }

    fun todosByCompleteness(completed: Boolean): List<Todo> =
        transaction {
            Todos.select {
                Todos.completed eq completed
            }.map(Todos::fromRow)
        }
}
