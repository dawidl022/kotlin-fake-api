package io.github.dawidl022

import io.github.dawidl022.db.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.github.dawidl022.plugins.*

object Config {
    val dataDir = System.getenv("DATA_DIR")
}

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
