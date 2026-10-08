package com.example.chessinsight.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val uuid: String,
    val url: String,
    val pgn: String?,
    val timeControl: String,
    val endTime: Long,
    val rated: Boolean,
    val rules: String,
    val whitePlayer: String,
    val whiteRating: Int,
    val whiteResult: String,
    val blackPlayer: String,
    val blackRating: Int,
    val blackResult: String,
    val importedAt: Long = System.currentTimeMillis(),
    val analyzed: Boolean = false
)
