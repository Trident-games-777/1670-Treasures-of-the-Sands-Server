package com.sands.treasure.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
//    val database = Database.connect(
//        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", user = "root", driver = "org.h2.Driver", password = ""
//    )

    val driverClassName = "org.h2.Driver"
    val jdbcURL = "jdbc:h2:file:./build/treasure"
    val database = Database.connect(jdbcURL, driverClassName)

    val playerService = PlayerService(database)
    routing {
        // Create user
        post("/players") {
            val player = call.receive<Player>()
            println("Receive player = $player")
            val id = playerService.create(player)
            println("Save player: $player")
            call.respond(HttpStatusCode.Created, id)
        }

        get("/players"){
            val players = playerService.leaderboardPlayers()
            call.respond(HttpStatusCode.OK, players)
        }

        get("/players/{id}") {
            val id = call.parameters["id"] ?: ""
            val player = playerService.player(id)
            println("Request player: $player")
            if (player != null) {
                call.respond(HttpStatusCode.OK, player)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("/players/{id}") {
            val id = call.parameters["id"] ?: ""
            val player = call.receive<Player>()
            playerService.update(id, player)
            call.respond(HttpStatusCode.OK)
        }

    }
}
