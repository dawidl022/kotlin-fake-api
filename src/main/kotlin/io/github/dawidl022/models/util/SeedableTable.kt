package io.github.dawidl022.models.util

import org.jetbrains.exposed.sql.Table
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

    fun get(recordId: Int) =
        transaction {
            select {
                this@SeedableTable.id eq recordId
            }.map(::fromRow).firstOrNull()
        }

    fun create(item: T) =
        transaction {
            val insertedId = insert {
                builderSchema(it, item)
            }[this@SeedableTable.id]
            get(insertedId)
        }

    fun update(item: T): T? {
        val itemId = item.id ?: return null
        return transaction {
            if (select { this@SeedableTable.id eq itemId }.count() == 0L) {
                return@transaction null
            }
            if (update({ this@SeedableTable.id eq itemId }) {
                    builderSchema(it, item)
                } == 0) return@transaction null
            get(itemId)
        }
    }

    fun delete(recordId: Int) =
        transaction {
            val deletedCount = deleteWhere {
                this@SeedableTable.id eq recordId
            }
            deletedCount > 0
        }
}
