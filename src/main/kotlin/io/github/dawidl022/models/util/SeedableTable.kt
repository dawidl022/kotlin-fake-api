package io.github.dawidl022.models.util

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.BatchInsertStatement

interface SeedableTable<T> {
    fun seed(): List<T>
    fun insertSchema(batch: BatchInsertStatement, item: T)
}
