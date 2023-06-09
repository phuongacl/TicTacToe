package com.acl.tictactoe.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acl.tictactoe.GameScreen
import com.acl.tictactoe.screen.AboutScreen
import com.acl.tictactoe.screen.MenuScreen
import com.acl.tictactoe.screen.OptionsScreen
import com.acl.tictactoe.screen.SplashScreen

@Composable
fun AppNavigationComponent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("menu") {
            MenuScreen(navController)
        }
        composable("game") {
            GameScreen(navController)
        }
        composable("options") {
            OptionsScreen(navController)
        }
        composable("about") {
            AboutScreen(navController)
        }
    }
}