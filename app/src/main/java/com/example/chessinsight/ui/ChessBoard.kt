package com.example.chessinsight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    boardState: Array<Array<String>> = defaultBoardState()
) {
    val context = LocalContext.current

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
                        val pieceCode = boardState[row][col]
                        if (pieceCode.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("file:///android_asset/pieces/\$pieceCode.svg")
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = pieceCode,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

fun defaultBoardState(): Array<Array<String>> {
    val board = Array(8) { Array(8) { "" } }
    
    // Black back rank
    val blackBackRank = arrayOf("bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR")
    for (i in 0..7) {
        board[0][i] = blackBackRank[i]
        board[1][i] = "bP"
    }
    
    // White back rank
    val whiteBackRank = arrayOf("wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR")
    for (i in 0..7) {
        board[6][i] = "wP"
        board[7][i] = whiteBackRank[i]
    }
    
    return board
}
