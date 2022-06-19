package io.github.dawidl022.models.util

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder

interface Idable {
    val id: Int?
}

abstract class SeedableTable<T : Idable>(val name: String) : Table() {
    abstract fun seed(): List<T>
    abstract fun <Key : Any> builderSchema(builder: UpdateBuilder<Key>, item: T)
    abstract fun fromRow(row: ResultRow): T
    abstract val id: Column<Int>

    fun all(): List<T> =
        transaction {
            selectAll().map(::fromRow)
        }

    fun get(recordId: Int): T? =
        transaction {
            select {
                this@SeedableTable.id eq recordId
            }.map(::fromRow).firstOrNull()
        }

    fun add(item: T): Boolean =
        transaction {
            insert {
                builderSchema(it, item)
            }
        }.insertedCount == 1

    fun delete(recordId: Int): Boolean =
        transaction {
            deleteWhere { this@SeedableTable.id eq recordId }
        } == 1

    fun put(recordId: Int, item: T) =
        transaction {
            update({ this@SeedableTable.id eq recordId }) {
                builderSchema(it, item)
            } == 1
        }
}
