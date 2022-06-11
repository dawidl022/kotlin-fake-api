package io.github.dawidl022.models.util

import io.github.dawidl022.models.Albums
import io.github.dawidl022.routes.noIdResponse
import io.github.dawidl022.routes.notFoundResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

abstract class SeedableTable<T>(val name: String) : Table() {
    abstract fun seed(): List<T>
    abstract fun insertSchema(batch: BatchInsertStatement, item: T)
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
        } ?:
        return notFoundResponse(call, name, queryId)
        call.respond(response(resource))
    }
}
