package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Todo
import io.github.dawidl022.models.Todos

class TodoMutation {
    fun createTodo(todo: Todo): Todo =
        Todos.create(todo)

    fun updateTodo(id: Int, todo: Todo): Todo =
        Todos.put(id, todo)

    fun deleteTodo(id: Int): Todo =
        Todos.delete(id)
}
