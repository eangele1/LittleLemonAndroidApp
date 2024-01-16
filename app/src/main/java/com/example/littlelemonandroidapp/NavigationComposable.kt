package com.example.littlelemonandroidapp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComposable(context: Context) {
    val navController = rememberNavController()

    // Determine start destination based on user data in shared preferences
    val startDestination = if (isUserDataAvailable(context)) {
        DestinationsImpl.home
    } else {
        DestinationsImpl.onboarding
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Define navigation routes for each destination
        composable(DestinationsImpl.home) {
            Home(navController, context)
        }
        composable(DestinationsImpl.profile) {
            Profile(navController, context)
        }
        composable(DestinationsImpl.onboarding) {
            Onboarding(navController, context)
        }
    }
}

private fun isUserDataAvailable(context: Context): Boolean {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    // Check if the required keys exist in SharedPreferences
    return sharedPreferences.contains("FirstName") &&
            sharedPreferences.contains("LastName") &&
            sharedPreferences.contains("EmailAddress")
}
