package com.sands.treasure.database

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TreasurePlayer(
    val nickname: String,
    val total: Int,
    val gadid: String,
    @BsonId
    var id: String = ObjectId().toString()
)