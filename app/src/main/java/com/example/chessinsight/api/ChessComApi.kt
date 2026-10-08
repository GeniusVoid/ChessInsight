package com.example.chessinsight.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChessComApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getPlayerProfile(username: String): Result<PlayerProfile> = withContext(Dispatchers.IO) {
        try {
            val response: PlayerProfile = client.get("https://api.chess.com/pub/player/\$username").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGameArchives(username: String): Result<GameArchives> = withContext(Dispatchers.IO) {
        try {
            val response: GameArchives = client.get("https://api.chess.com/pub/player/\$username/games/archives").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMonthlyGames(url: String): Result<MonthlyGames> = withContext(Dispatchers.IO) {
        try {
            val response: MonthlyGames = client.get(url).body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
