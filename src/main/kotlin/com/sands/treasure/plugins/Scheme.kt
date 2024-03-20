package com.sands.treasure.plugins

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sands.treasure.database.TreasurePlayer
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.jetbrains.exposed.sql.*
import kotlin.random.Random

@Serializable
data class Player(val nickname: String, val total: Int, val gadid: String)

class PlayerService(private val database: MongoDatabase) {
    private fun map(player: Player?): TreasurePlayer? = if (player == null) null else TreasurePlayer(
        nickname = player.nickname, total = player.total, gadid = player.gadid
    )

    private fun map(treasurePlayer: TreasurePlayer?): Player? = if (treasurePlayer == null) null else Player(
        nickname = treasurePlayer.nickname, total = treasurePlayer.total, gadid = treasurePlayer.gadid
    )

    //GOOD
    suspend fun create(player: Player) {
        val collection = database.getCollection<TreasurePlayer>(collectionName = "treasurePlayers")
        map(player)?.let {
            collection.insertOne(it)
        }
    }

    //GOOD
    suspend fun player(gadid: String): Player? {
        val collection = database.getCollection<TreasurePlayer>(collectionName = "treasurePlayers")
        val queryParams = Filters.eq("gadid", gadid)
        return map(collection.find<TreasurePlayer>(queryParams).firstOrNull())
    }

    //GOOD
    suspend fun leaderboardPlayers(): List<Player> {
        val treasurePlayers: List<TreasurePlayer> =
            database.getCollection<TreasurePlayer>(collectionName = "treasurePlayers").find<TreasurePlayer>().toList()

        return treasurePlayers.map {
            map(it)!!
        }
    }

    //GOOD
    suspend fun update(gadid: String, player: Player) {
        val collection = database.getCollection<TreasurePlayer>(collectionName = "treasurePlayers")
        val queryParam = Filters.eq("gadid", gadid)
        val updateParams = Updates.set("total", player.total)
        collection.updateOne(filter = queryParam, update = updateParams)
    }
}
