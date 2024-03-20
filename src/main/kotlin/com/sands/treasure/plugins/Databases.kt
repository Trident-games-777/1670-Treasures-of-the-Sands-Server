package com.sands.treasure.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDatabases() {

    val connectionString =
        "mongodb+srv://yevhenmoiseiev:mais1208@cluster0.rilg0du.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val client = MongoClient.create(connectionString = connectionString)
    val database = client.getDatabase(databaseName = "TreasurePlayers")
    val playerService = PlayerService(database)

    routing {
        // Create user
        post("/players") {
            val player = call.receive<Player>()
            val id = playerService.create(player)
            println("Save player: $player")
            call.respond(HttpStatusCode.Created)
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
