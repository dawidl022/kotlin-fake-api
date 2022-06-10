package io.github.dawidl022

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.github.dawidl022.plugins.*

const val DATA_DIR = "src/main/kotlin/io/github/dawidl022/data/"

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
