package com.example.matapp.screens

import BottomNavBar
import Screen
import TopNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.ui.theme.MatappTheme
import com.google.firebase.auth.FirebaseAuth



@Composable
fun ShowRecipeLayout(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopNavBar(
            nameString = "Recipe",
            onHamburgerMenuClick = {
                navController.navigate(Screen.Create.route)
            },
            onProfilePictureClick = {
                navController.navigate(Screen.Profile.route)
            }
        )




        Spacer(modifier = Modifier.weight(1f))
        BottomNavBar(
            onForYouClick = {
                navController.navigate(Screen.ForYou.route)
            },
            onSearchClick = {
                navController.navigate(Screen.Search.route)
            },
            onSavedClick = {
                navController.navigate(Screen.Saved.route)
            },
            onSettingsClick = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}

@Composable
fun CreateShowRecipeScreen(navController: NavController) {
    ShowRecipeLayout(navController)
}

@Preview
@Composable
fun CreateShowRecipePreview() {
    val navController: NavController = rememberNavController()

    MatappTheme {
        CreateShowRecipeScreen(navController = navController)
    }
}