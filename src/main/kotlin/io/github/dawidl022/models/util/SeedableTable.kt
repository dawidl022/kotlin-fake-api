package io.github.dawidl022.models.util

import com.expediagroup.graphql.generator.annotations.GraphQLValidObjectLocations
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
}
