package com.example.littlelemonandroidapp

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.littlelemonandroidapp.ui.theme.Gold

@Composable
fun Profile(navController: NavController, context: Context) {
    ProfileHeader()

    // Retrieve user information from shared preferences
    val firstName = getFirstNameFromSharedPreferences(context)
    val lastName = getLastNameFromSharedPreferences(context)
    val emailAddress = getEmailAddressFromSharedPreferences(context)

    // Display user registration information using multiple Text Composable and Column Composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        // Add the text "Profile information:" using the Text Composable
        Text(
            "Profile information:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )

        // Present Text to display the user’s first name
        Text("First Name: $firstName", fontSize = 16.sp)

        // Present Text to display the user’s last name
        Text("Last Name: $lastName", fontSize = 16.sp)

        // Present Text to display the user’s email address
        Text("Email Address: $emailAddress", fontSize = 16.sp)

        // Add the Logout Button Composable
        LogoutButton(navController, context)
    }
}

@Composable
fun LogoutButton(navController: NavController, context: Context) {
    // Use mutableStateOf to manage the state of the bottom sheet
    var isLogoutConfirmationVisible by remember { mutableStateOf(false) }

    // Logout Button Composable
    Button(
        onClick = {
            // Show the confirmation modal
            isLogoutConfirmationVisible = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Gold)
    ) {
        Text("Log out")
    }

    // Confirmation modal using AlertDialog
    if (isLogoutConfirmationVisible) {
        AlertDialog(onDismissRequest = {
            // Dismiss the dialog if the user clicks outside
            isLogoutConfirmationVisible = false
        },
            title = { Text("Confirm") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Gold),
                    onClick = {
                        clearSharedPreferences(context)

                        // Dismiss the dialog
                        isLogoutConfirmationVisible = false

                        navigateToOnboardingScreen(navController)
                    }) {
                    Text("Log out")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    onClick = {
                        // Dismiss the dialog
                        isLogoutConfirmationVisible = false
                    }) {
                    Text("Cancel")
                }
            })
    }
}


private fun clearSharedPreferences(context: Context) {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    // Clear SharedPreferences data
    sharedPreferences.edit().clear().apply()
}

private fun getFirstNameFromSharedPreferences(context: Context): String {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    return sharedPreferences.getString("FirstName", "") ?: ""
}

private fun getLastNameFromSharedPreferences(context: Context): String {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    return sharedPreferences.getString("LastName", "") ?: ""
}

private fun getEmailAddressFromSharedPreferences(context: Context): String {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    return sharedPreferences.getString("EmailAddress", "") ?: ""
}


@Composable
fun ProfileHeader() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(25.dp),
        contentScale = ContentScale.Fit
    )
}

private fun navigateToOnboardingScreen(navController: NavController) {
    navController.navigate("onboarding"){
        popUpTo(navController.graph.id){
            inclusive = true
        }
    }
}