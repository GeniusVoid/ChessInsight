package com.example.chessinsight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnalysisScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            // Engine Evaluation Bar
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .background(Color.DarkGray)
                        .align(Alignment.TopCenter)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Chess Board
            ChessBoard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Move List
        Text(
            text = "Move List",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray.copy(alpha = 0.2f))
                .padding(8.dp)
        ) {
            val dummyMoves = listOf("1. e4 e5", "2. Nf3 Nc6", "3. Bb5 a6", "4. Ba4 Nf6")
            items(dummyMoves) { move ->
                Text(
                    text = move,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
