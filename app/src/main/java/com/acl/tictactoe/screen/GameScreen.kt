package com.acl.tictactoe

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.acl.tictactoe.component.GridBoard
import com.acl.tictactoe.component.Player
import com.acl.tictactoe.component.checkWinningConditions
import com.acl.tictactoe.theme.primaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavHostController) {

    var currentPlayer by remember { mutableStateOf(Player.X) }
    val board = remember { mutableStateListOf(*Array<Player?>(9) { null }) }
    val gameEnded = remember { mutableStateOf(false) }
    val winner = remember { mutableStateOf<Player?>(null) }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "TIC TAC TOE",
                        textAlign = TextAlign.Center
                    )
                },
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
                        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
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
                        .background(
                            color = primaryColor,
                            shape = RoundedCornerShape(100f)
                        )
                        .clickable {
                            for (index in board.indices) {
                                board[index] = null
                            }
                            currentPlayer = Player.X
                            gameEnded.value = false
                            winner.value = null
                        },
                    text = "Reset",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(64.dp)
                )
                Text(
                    text = "Powered by American Code Lab",
                    style = MaterialTheme.typography.h5,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    )
}