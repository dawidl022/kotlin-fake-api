package io.github.dawidl022.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

data class Todo(override val id: Int?, val userId: Int, val title: String, val completed: Boolean) : Idable {
    constructor(id: String, userId: String, title: String, completed: String) :
            this(
                id.toInt(), userId.toInt(), title, completed == "true"
            )
}

object Todos : InMemoryResource<Todo>() {
    override val storage: MutableList<Todo> by lazy {
        XMLParser.parse("todos.xml", TodosXML::class.java).toMutableList()
    }
}

@JsonRootName("todo")
data class TodoXML(
    @set:JsonProperty("userId")
    var userId: String? = null,

    @set:JsonProperty("id")
    var id: String? = null,

    @set:JsonProperty("title")
    var title: String? = null,

    @set:JsonProperty("completed")
    var completed: String? = null
)

@JsonRootName("todos")
data class TodosXML(
    @set:JsonProperty("todo")
    var todos: List<TodoXML> = mutableListOf()
) : XMLParsable<List<Todo>> {
    override val data: List<Todo>
        get() = todos.map {
            Todo(it.id ?: "", it.userId ?: "", it.title ?: "", it.completed ?: "")
        }
}
