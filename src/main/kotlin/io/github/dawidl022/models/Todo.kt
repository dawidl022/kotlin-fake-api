package io.github.dawidl022.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import io.github.dawidl022.models.util.Idable
import io.github.dawidl022.models.util.SeedableTable
import io.github.dawidl022.models.util.XMLParsable
import io.github.dawidl022.models.util.XMLParser
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
data class Todo(override val id: Int?, val userId: Int, val title: String, val completed: Boolean) : Idable {
    constructor(id: String, userId: String, title: String, completed: String) :
            this(
                id.toInt(), userId.toInt(), title, completed == "true"
            )
}

object Todos : SeedableTable<Todo>("todo") {
    override val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val title = varchar("title", 255)
    val completed = bool("completed")

    override fun seed(): List<Todo> =
        XMLParser.parse("todos.xml", TodosXML::class.java).toMutableList()

    override fun fromRow(row: ResultRow) =
        Todo(
            id = row[id],
            userId = row[userId],
            title = row[title],
            completed = row[completed]
        )

    override fun <Key : Any> builderSchema(builder: UpdateBuilder<Key>, item: Todo) {
        builder[userId] = item.userId
        builder[title] = item.title
        builder[completed] = item.completed
    }
}

@JsonRootName("todo")
private data class TodoXML(
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
private data class TodosXML(
    @set:JsonProperty("todo")
    var todos: List<TodoXML> = mutableListOf()
) : XMLParsable<List<Todo>> {
    override val data: List<Todo>
        get() = todos.map {
            Todo(it.id ?: "", it.userId ?: "", it.title ?: "", it.completed ?: "")
        }
}
