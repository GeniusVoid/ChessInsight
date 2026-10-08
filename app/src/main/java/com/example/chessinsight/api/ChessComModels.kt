package com.example.chessinsight.api

import kotlinx.serialization.Serializable

@Serializable
data class PlayerProfile(
    val avatar: String? = null,
    val player_id: Int,
    val url: String,
    val username: String,
    val name: String? = null,
    val title: String? = null,
    val followers: Int,
    val country: String,
    val last_online: Long,
    val joined: Long,
    val status: String,
    val is_streamer: Boolean
)

@Serializable
data class GameArchives(
    val archives: List<String>
)

@Serializable
data class MonthlyGames(
    val games: List<ChessGame>
)

@Serializable
data class ChessGame(
    val url: String,
    val pgn: String? = null,
    val time_control: String,
    val end_time: Long,
    val rated: Boolean,
    val tcn: String? = null,
    val uuid: String,
    val rules: String,
    val white: PlayerInGame,
    val black: PlayerInGame
)

@Serializable
data class PlayerInGame(
    val rating: Int,
    val result: String,
    val username: String
)
