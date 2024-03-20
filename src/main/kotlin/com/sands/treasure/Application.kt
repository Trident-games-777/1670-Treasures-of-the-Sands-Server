package com.sands.treasure

import com.sands.treasure.plugins.configureDatabases
import com.sands.treasure.plugins.configureRouting
import com.sands.treasure.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    //embeddedServer(Netty, port = 8100, host = "192.168.0.102", module = Application::module)
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()
}
