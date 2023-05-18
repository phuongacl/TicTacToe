package com.acl.tictactoe.component

import androidx.compose.runtime.MutableState

fun checkWinningConditions(
    board: List<Player?>,
    gameEnded: MutableState<Boolean>,
    winner: MutableState<Player?>
) {
    val winningPositions = listOf(
        // Rows
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        // Columns
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        // Diagonals
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    for (positions in winningPositions) {
        val (a, b, c) = positions

        if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
            // Game has been won
            // Update game state
            gameEnded.value = true
            winner.value = board[a]
            return
        }
    }

    // Check for a draw
    if (board.all { it != null }) {
        // Game has ended in a draw
        // Update game state
        gameEnded.value = true
        winner.value = null
    }
}