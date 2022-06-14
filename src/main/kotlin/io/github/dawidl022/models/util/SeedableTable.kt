package io.github.dawidl022.models.util

import com.expediagroup.graphql.generator.annotations.GraphQLValidObjectLocations
import io.github.dawidl022.models.Albums
import io.github.dawidl022.resolvers.FailedOperationException
import io.github.dawidl022.resolvers.RecordNotFoundWithIdException
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder

interface Idable {
    @GraphQLValidObjectLocations([GraphQLValidObjectLocations.Locations.OBJECT])
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

    fun get(recordId: Int): T =
        getOrNull(recordId) ?: throw RecordNotFoundWithIdException(name, recordId)

    fun create(item: T): T =
        transaction {
            val insertedId = insert {
                builderSchema(it, item)
            }[this@SeedableTable.id]
            getOrNull(insertedId) ?: throw FailedOperationException("create", name)
        }

    fun put(recordId: Int, item: T): T =
        transaction {
            throwIfRecordNonExistent(recordId)
            if (update({ this@SeedableTable.id eq recordId }) {
                    builderSchema(it, item)
                } == 0) throw FailedOperationException("update", name, recordId)
            get(recordId)
        }

    fun delete(recordId: Int): T =
        transaction {
            val deletedRecord = getOrNull(recordId) ?: throw RecordNotFoundWithIdException(name, recordId)

            val deletedCount = deleteWhere {
                this@SeedableTable.id eq recordId
            }
            if (deletedCount == 0) {
                throw FailedOperationException("delete", name, recordId)
            }

            return@transaction deletedRecord
        }

    private fun getOrNull(recordId: Int): T? =
        transaction {
            select {
                this@SeedableTable.id eq recordId
            }.map(::fromRow).firstOrNull()
        }

    private fun throwIfRecordNonExistent(recordId: Int) {
        if (select { this@SeedableTable.id eq recordId }.count() == 0L) {
            throw RecordNotFoundWithIdException(name, recordId)
        }
    }
}
