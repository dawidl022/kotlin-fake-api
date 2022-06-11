package io.github.dawidl022.models.util

import io.github.dawidl022.models.Albums
import io.github.dawidl022.routes.noIdResponse
import io.github.dawidl022.routes.notFoundResponse
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

interface Idable {
    val id: Int?
}

abstract class SeedableTable<T : Idable>(val name: String) : Table() {
    abstract fun seed(): List<T>
    abstract fun <Key : Any> insertSchema(insert: InsertStatement<Key>, item: T)
    abstract fun fromRow(row: ResultRow): T
    abstract val id: Column<Int>

    fun all(): List<T> =
        transaction {
            selectAll().map(::fromRow)
        }

    suspend fun get(call: ApplicationCall, response: (T) -> Any) {
        val queryId = call.parameters["id"] ?: return noIdResponse(call)
        val resource = transaction {
            select {
                this@SeedableTable.id eq queryId.toInt()
            }.map(::fromRow).firstOrNull()
        } ?: return notFoundResponse(call, name, queryId)
        call.respond(response(resource))
    }

    suspend inline fun <reified E : T> add(call: ApplicationCall) {
        val item = call.receive<E>()
        val insertedCount = transaction {
            insert {
                insertSchema(it, item)
            }
        }.insertedCount

        if (insertedCount > 0) {
            call.respondText(
                "Added $name.",
                status = HttpStatusCode.Accepted
            )
        } else {
            call.respondText(
                "Failed to add $name",
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    suspend fun delete(call: ApplicationCall) {
        val queryId = call.parameters["id"] ?: return noIdResponse(call)
        val deletedCount = transaction {
            deleteWhere { this@SeedableTable.id eq queryId.toInt() }
        }

        if (deletedCount > 0) {
            call.respondText(
                "${name.replaceFirstChar(Char::uppercase)} removed.",
                status = HttpStatusCode.Accepted,
            )
        } else {
            notFoundResponse(call, name, queryId)
        }
    }
}
