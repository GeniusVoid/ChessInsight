package com.example.chessinsight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    boardState: Array<Array<String>> = defaultBoardState()
) {
    Column(modifier = modifier.aspectRatio(1f)) {
        for (row in 0..7) {
            Row(modifier = Modifier.weight(1f)) {
                for (col in 0..7) {
                    val isLightSquare = (row + col) % 2 == 0
                    val squareColor = if (isLightSquare) Color(0xFFF0D9B5) else Color(0xFFB58863)
                    
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(squareColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = boardState[row][col],
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

fun defaultBoardState(): Array<Array<String>> {
    val board = Array(8) { Array(8) { "" } }
    
    val backRank = arrayOf("♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖")
    
    for (i in 0..7) {
        board[0][i] = backRank[i]
        board[1][i] = "♙"
        board[6][i] = "♙"
        board[7][i] = backRank[i]
    }
    return board
}
