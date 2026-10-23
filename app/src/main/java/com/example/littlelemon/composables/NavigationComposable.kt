package com.example.littlelemon.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val isLoggedIn = !prefs.getString("email", "").isNullOrBlank()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Home.route else Onboarding.route
    ) {
        composable(Onboarding.route) { Onboarding(navController) }
        composable(Home.route) { Home(navController) }
        composable(Profile.route) { Profile(navController) }
    }
}