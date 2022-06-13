package io.github.dawidl022

import com.apurebase.kgraphql.KGraphQL
import io.github.dawidl022.db.DatabaseFactory
import io.github.dawidl022.models.Albums
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.github.dawidl022.plugins.*

object Config {
    val dataDir = System.getenv("DATA_DIR")
}

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
