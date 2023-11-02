package com.example.matapp

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

@Composable
fun SavedLayout(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopNavBar(
            onHamburgerMenuClick = {
                navController.navigate(Screen.Create.route)
            },
            onProfilePictureClick = {
                navController.navigate(Screen.Profile.route)
            },
            nameString = "Saved"
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
fun CreateSavedScreen(navController: NavController) {
    SavedLayout(navController)
}

@Preview
@Composable
fun CreateSavedPreview() {

    val navController: NavController = rememberNavController()

    MatappTheme {
        CreateSavedScreen(navController = navController)
    }
}