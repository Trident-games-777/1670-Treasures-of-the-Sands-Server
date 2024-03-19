package com.sands.treasure.plugins

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*

@Serializable
data class Player(val nickname: String, val total: Int, val gadid: String)

class PlayerService(private val database: Database) {
    object PlayerTable : Table() {
        val id = integer("id").autoIncrement()
        val nickname = varchar("nickname", length = 50)
        val total = integer("total")
        val gadid = varchar("gadid", length = 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(PlayerTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(player: Player): Int = dbQuery {
        println("create player server = $player")
        PlayerTable.insert {
            it[nickname] = player.nickname
            it[total] = player.total
            it[gadid] = player.gadid
        }[PlayerTable.id]
    }

    suspend fun player(gadid: String): Player? {
        return dbQuery {
            PlayerTable.select { PlayerTable.gadid eq gadid }.map {
                Player(
                    nickname = it[PlayerTable.nickname], total = it[PlayerTable.total], gadid = gadid
                )
            }.singleOrNull()
        }
    }

    suspend fun leaderboardPlayers(): List<Player> {
        return dbQuery {
            PlayerTable.selectAll().map {
                Player(
                    nickname = it[PlayerTable.nickname], total = it[PlayerTable.total], gadid = it[PlayerTable.gadid]
                )
            }
        }
    }

    suspend fun update(gadid: String, player: Player) {
        dbQuery {
            PlayerTable.update({ PlayerTable.gadid eq gadid}) {
                it[nickname] = player.nickname
                it[total] = player.total
            }
        }
    }
}
