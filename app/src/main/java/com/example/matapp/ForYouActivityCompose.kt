package com.example.matapp

import BottomNavBar
import TopNavBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ForYouLayout(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        ) {

       TopNavBar(
           nameString = "For You",
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