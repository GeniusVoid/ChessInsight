package com.example.chessinsight.sync

import com.example.chessinsight.api.ChessComApi
import com.example.chessinsight.data.GameDao
import com.example.chessinsight.data.GameEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SyncRepository(
    private val api: ChessComApi,
    private val gameDao: GameDao
) {
    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState

    suspend fun syncGames(username: String) {
        _syncState.value = SyncState.Syncing("Fetching profile...")
        val profileResult = api.getPlayerProfile(username)
        if (profileResult.isFailure) {
            _syncState.value = SyncState.Error("Profile not found or API error.")
            return
        }

        _syncState.value = SyncState.Syncing("Fetching archives...")
        val archivesResult = api.getGameArchives(username)
        if (archivesResult.isFailure) {
            _syncState.value = SyncState.Error("Could not fetch archives.")
            return
        }

        val archives = archivesResult.getOrNull()?.archives ?: emptyList()
        var totalImported = 0

        for (archiveUrl in archives.reversed()) { // Sync from newest to oldest
            _syncState.value = SyncState.Syncing("Importing: \$archiveUrl")
            val gamesResult = api.getMonthlyGames(archiveUrl)
            if (gamesResult.isSuccess) {
                val games = gamesResult.getOrNull()?.games ?: emptyList()
                val entities = games.map { game ->
                    GameEntity(
                        uuid = game.uuid,
                        url = game.url,
                        pgn = game.pgn,
                        timeControl = game.time_control,
                        endTime = game.end_time,
                        rated = game.rated,
                        rules = game.rules,
                        whitePlayer = game.white.username,
                        whiteRating = game.white.rating,
                        whiteResult = game.white.result,
                        blackPlayer = game.black.username,
                        blackRating = game.black.rating,
                        blackResult = game.black.result
                    )
                }
                gameDao.insertAll(entities)
                totalImported += entities.size
            }
        }
        _syncState.value = SyncState.Success("Imported \$totalImported games.")
    }
}

sealed class SyncState {
    object Idle : SyncState()
    data class Syncing(val message: String) : SyncState()
    data class Success(val message: String) : SyncState()
    data class Error(val message: String) : SyncState()
}
