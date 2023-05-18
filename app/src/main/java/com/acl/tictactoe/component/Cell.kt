package com.acl.tictactoe.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.acl.tictactoe.theme.primaryColor

@Composable
fun Cell(player: Player?, onCellClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(width = 1.dp, color = Color.White)
            .background(color = primaryColor, shape = RectangleShape)
            .clickable(
                enabled = player == null,
                onClick = onCellClicked
            ),
        contentAlignment = Alignment.Center
    ) {
        when (player) {
            Player.X -> Text("X", color = Color.White, style = MaterialTheme.typography.h4)
            Player.O -> Text("O", color = Color.White, style = MaterialTheme.typography.h4)
            null -> {}
        }
    }
}