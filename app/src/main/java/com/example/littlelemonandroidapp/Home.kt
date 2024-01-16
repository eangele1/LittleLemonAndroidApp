package com.example.littlelemonandroidapp

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonandroidapp.ui.theme.DarkGreen
import com.example.littlelemonandroidapp.ui.theme.Gold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf

@Composable
fun Home(navController: NavController, context: Context) {
    val databaseMenuItems = AppDatabase.getDatabase(context).menuItemDao().getAll().observeAsState(emptyList())
    val searchPhrase = remember { mutableStateOf("") }
    val categorySort = remember { mutableStateOf("") }

    var menuItems = if (categorySort.value.isNotEmpty()) {
        databaseMenuItems.value.filter { it.category.contains(categorySort.value, ignoreCase = true) }
    } else {
        databaseMenuItems.value
    }

    if (searchPhrase.value.isNotEmpty()) {
        menuItems = databaseMenuItems.value.filter { it.title.contains(searchPhrase.value, ignoreCase = true) }
    }

    // Create a Column composable to hold the content vertically
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Add the header with the profile image button on the right
        Header(navController)

        // Add the hero section below the header
        HeroSection(searchPhrase)

        // Add the menu filtering buttons
        FilterByCategoryButtons(categorySort)

        // Add the scrollable list of menu items
        MenuItemsList(menuItems)
    }
}

@Composable
fun HeroSection(searchPhrase: MutableState<String>) {
    // Use Box composable to overlay content
    Column(
        modifier = Modifier.background(DarkGreen).padding(16.dp)
    ) {
        // Use Row to position the image and text columns side by side
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Column for text components
            Column(
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    style = TextStyle(
                        color = Gold,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(text = "Chicago", style = TextStyle(color = Color.White, fontSize = 16.sp))
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist!",
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }

            // Column for the hero image
            Column(
                modifier = Modifier.weight(1f, fill = false).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = null,
                    modifier = Modifier.width(125.dp).height(150.dp).padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Add the search bar
        OutlinedTextField(
            value = searchPhrase.value,
            onValueChange = { searchPhrase.value = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            label = { Text(text = "Search") },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
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
            )
        )
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1.0F))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.width(220.dp).height(100.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier.size(50.dp).clip(CircleShape).clickable {
                // Navigate to the Profile screen on click
                navController.navigate("profile")
            }
        )

        Spacer(modifier = Modifier.width(30.dp))
    }
}

@Composable
fun FilterByCategoryButtons(categorySort: MutableState<String>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Order for Delivery!",
            style = TextStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Row for buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonComponent("Starters", categorySort)
            ButtonComponent("Mains", categorySort)
            ButtonComponent("Desserts", categorySort)
            ButtonComponent("Drinks", categorySort)
        }
    }
}

@Composable
fun ButtonComponent(text: String, categorySort: MutableState<String>) {
    // Customize button appearance as needed
    TextButton(
        onClick = {
            categorySort.value = if (categorySort.value == text) "" else text
        },
        modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(50)).background(Color.LightGray)
    ) {
        Text(text = text, color = Color.DarkGray)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemsList(menuItems: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        items(items = menuItems, itemContent = { menuItem ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display title, description, and price
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = menuItem.title, fontWeight = FontWeight.Bold)
                    Text(text = menuItem.description, fontSize = 14.sp)
                    Text(text = "$${menuItem.price}", fontWeight = FontWeight.Bold, color = Gold)
                }

                // Load image using Glide library
                GlideImage(
                    model = menuItem.image,
                    contentDescription = null,
                    modifier = Modifier.width(80.dp).height(80.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }
        })
    }
}
