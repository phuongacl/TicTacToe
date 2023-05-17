package com.acl.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acl.tictactoe.Player.*
import com.acl.tictactoe.ui.theme.TicTacToeTheme
import com.acl.tictactoe.ui.theme.primaryColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TicTacToeGame()
                }
            }
        }
    }
}

enum class Player {
    X, O
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TicTacToeGame() {
    var currentPlayer by remember { mutableStateOf(X) }
    val board = remember { mutableStateListOf(*Array<Player?>(9) { null }) }
    val gameEnded = remember { mutableStateOf(false) }
    val winner = remember { mutableStateOf<Player?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tic Tac Toe") },
                backgroundColor = primaryColor,
                contentColor = Color.White
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(64.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (gameEnded.value) {
                        if (winner.value != null) {
                            "Winner: ${winner.value}"
                        } else {
                            "Game Drawn!"
                        }
                    } else {
                        "Current player: $currentPlayer"
                    },
                    style = MaterialTheme.typography.h5,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                GridBoard(
                    board = board,
                    gameEnded = gameEnded,
                    winner = winner
                ) { index ->
                    if (!gameEnded.value && board[index] == null) {
                        board[index] = currentPlayer
                        currentPlayer = if (currentPlayer == X) O else X
                        checkWinningConditions(
                            board = board,
                            gameEnded = gameEnded,
                            winner = winner
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    modifier = Modifier
                        .width(120.dp)
                        .height(64.dp)
                        .padding(vertical = 16.dp)
                        .background(color = primaryColor, shape = RoundedCornerShape(100f))
                        .clickable {
                            for (index in board.indices) {
                                board[index] = null
                            }
                            currentPlayer = X
                            gameEnded.value = false
                            winner.value = null
                        },
                    text = "RESET",
                    color = Color.White,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

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
                    .border(width = 1.dp, color = Color.Black)
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

@Composable
fun Cell(player: Player?, onCellClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(width = 1.dp, color = Color.Black)
            .background(color = primaryColor, shape = RectangleShape)
            .clickable(
                enabled = player == null,
                onClick = onCellClicked
            ),
        contentAlignment = Alignment.Center
    ) {
        when (player) {
            X -> Text("X", style = MaterialTheme.typography.h4)
            O -> Text("O", style = MaterialTheme.typography.h4)
            null -> {}
        }
    }
}

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

