package com.example.chessinsight.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chessinsight.data.GameEntity
import com.example.chessinsight.sync.SyncState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    syncState: SyncState,
    games: List<GameEntity>,
    onSyncClick: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChessInsight") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Analyze your Chess.com games", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Chess.com Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onSyncClick(username) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sync Games")
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            when (syncState) {
                is SyncState.Syncing -> Text(syncState.message, color = MaterialTheme.colorScheme.primary)
                is SyncState.Success -> Text(syncState.message, color = MaterialTheme.colorScheme.primary)
                is SyncState.Error -> Text(syncState.message, color = MaterialTheme.colorScheme.error)
                else -> {}
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Imported Games (\${games.size})", style = MaterialTheme.typography.titleMedium)
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(games) { game ->
                    GameCard(game)
                }
            }
        }
    }
}

@Composable
fun GameCard(game: GameEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("\${game.whitePlayer} vs \${game.blackPlayer}")
            Text("\${game.timeControl} - \${game.whiteResult} / \${game.blackResult}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
