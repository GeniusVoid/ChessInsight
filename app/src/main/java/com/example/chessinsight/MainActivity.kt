package com.example.chessinsight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.chessinsight.api.ChessComApi
import com.example.chessinsight.data.AppDatabase
import com.example.chessinsight.sync.SyncRepository
import com.example.chessinsight.ui.DashboardScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val api = ChessComApi()
        val syncRepository = SyncRepository(api, database.gameDao())

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val syncState by syncRepository.syncState.collectAsState()
                    val games by database.gameDao().getAllGames().collectAsState(initial = emptyList())
                    
                    DashboardScreen(
                        syncState = syncState,
                        games = games,
                        onSyncClick = { username ->
                            lifecycleScope.launch {
                                syncRepository.syncGames(username)
                            }
                        }
                    )
                }
            }
        }
    }
}
