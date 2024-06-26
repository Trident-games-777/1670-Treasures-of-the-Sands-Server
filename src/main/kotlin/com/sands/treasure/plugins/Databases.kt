package com.sands.treasure.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay

fun Application.configureDatabases() {

    /*val connectionString =
        "mongodb+srv://yevhenmoiseiev:mais1208@cluster0.rilg0du.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val client = MongoClient.create(connectionString = connectionString)
    val database = client.getDatabase(databaseName = "TreasurePlayers")
    val playerService = PlayerService(database)*/

    routing {
       /* post("/players") {
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
        }*/

        get("/link"){
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }

        get("/link1"){
            delay(1000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }
        get("/link2"){
            delay(2000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }
        get("/link3"){
            delay(3000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }
        get("/link4"){
            delay(4000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }
        get("/link5"){
            delay(5000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }
        get("/link6"){
            delay(6000)
            println("=========================REQUEST===============================")
            val a = call.request.queryParameters["a"]
            val b = call.request.queryParameters["b"]
            call.respondText("https://${a}_and_${b}")
        }

    }
}
