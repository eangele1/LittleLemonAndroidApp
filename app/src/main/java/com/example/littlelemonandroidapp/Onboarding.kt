package com.example.littlelemonandroidapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import android.content.Context
import androidx.navigation.NavController
import com.example.littlelemonandroidapp.ui.theme.DarkGreen
import com.example.littlelemonandroidapp.ui.theme.Gold

@Composable
fun Onboarding(navController: NavController, context: Context) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var registrationStatus by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        OnboardingHeader()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGreen)
                .padding(vertical = 16.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                "Let's get to know you!",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Personal Information Section
            PersonalInformationSection(
                firstName = firstName,
                lastName = lastName,
                emailAddress = emailAddress,
                onFirstNameChange = { firstName = it },
                onLastNameChange = { lastName = it },
                onEmailAddressChange = { emailAddress = it }
            )

            // Register Button
            Button(
                onClick = {
                    // Validate input fields
                    if (firstName.isBlank() || lastName.isBlank() || emailAddress.isBlank()) {
                        registrationStatus = "Registration unsuccessful. Please enter all data."
                    } else {
                        // Save user data in SharedPreferences
                        saveUserData(context, firstName, lastName, emailAddress)
                        registrationStatus = "Registration successful!"
                        // Navigate to Home screen
                        navController.navigate("home"){
                            popUpTo(navController.graph.id){
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gold)
            ) {
                Text("Register")
            }

            // Display registration status
            Text(
                registrationStatus,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Add a function to save user data in SharedPreferences
private fun saveUserData(
    context: Context,
    firstName: String,
    lastName: String,
    emailAddress: String
) {
    // Get SharedPreferences instance
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    // Use SharedPreferences.Editor to save data
    val editor = sharedPreferences.edit()
    editor.putString("FirstName", firstName)
    editor.putString("LastName", lastName)
    editor.putString("EmailAddress", emailAddress)

    // Apply changes
    editor.apply()
}

@Composable
fun PersonalInformationSection(
    firstName: String,
    lastName: String,
    emailAddress: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailAddressChange: (String) -> Unit
) {
    Text("Personal Information", style = MaterialTheme.typography.titleMedium)

    // TextField for first name
    TextInputField(
        value = firstName,
        onValueChange = onFirstNameChange,
        label = "First Name",
        imeAction = ImeAction.Next
    )

    // TextField for last name
    TextInputField(
        value = lastName,
        onValueChange = onLastNameChange,
        label = "Last Name",
        imeAction = ImeAction.Next
    )

    // TextField for email address
    TextInputField(
        value = emailAddress,
        onValueChange = onEmailAddressChange,
        label = "Email Address",
        imeAction = ImeAction.Done
    )
}

@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            cursorColor = Color.Black
        ),
    )
}

@Composable
fun OnboardingHeader() {
    Image(
        painter = painterResource(id = R.drawable.logo), // Replace R.drawable.logo with the actual resource ID of your logo
        contentDescription = null, // Provide a proper content description
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Adjust the height as needed
            .padding(25.dp),
        contentScale = ContentScale.Fit
    )
}
