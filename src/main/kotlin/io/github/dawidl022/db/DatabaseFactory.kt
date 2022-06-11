package io.github.dawidl022.db

import io.github.dawidl022.models.Albums
import io.github.dawidl022.models.Photo
import io.github.dawidl022.models.Photos
import io.github.dawidl022.models.Todos
import io.github.dawidl022.models.util.SeedableTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/kotlin_fake_api", driver = "org.postgresql.Driver",
            user = "user", password = "password"
        )

        transaction {
            SchemaUtils.create(Albums)
            SchemaUtils.create(Photos)
            SchemaUtils.create(Todos)
        }
        seedDb(Albums)
        seedDb(Photos)
        seedDb(Todos)
    }

    private fun <T : SeedableTable<M>, M> seedDb(table: T) {
        if (transaction {
                table.selectAll().count() == 0L
            }) {
            transaction {
                table.batchInsert(table.seed()) {
                    table.insertSchema(this, it)
                }
            }
        }
    }
}
