package com.acl.tictactoe.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridBoard(
    board: List<Player?>,
    gameEnded: MutableState<Boolean>,
    winner: MutableState<Player?>,
    onCellClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.border(width = 1.dp, color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until 3) {
            Row(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.White)
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    Cell(board[index]) {
                        if (board[index] == null) {
                            onCellClicked(index)
                            checkWinningConditions(
                                board = board,
                                gameEnded = gameEnded,
                                winner = winner
                            )
                        }
                    }
                }
            }
        }
    }
}