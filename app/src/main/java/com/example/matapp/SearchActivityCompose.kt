package com.example.matapp

import BottomNavBar
import Screen
import TopNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.ui.theme.MatappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayout(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopNavBar(
            nameString = "Search",
            onHamburgerMenuClick = {
                navController.navigate(Screen.Create.route)
            },
            onProfilePictureClick = {
                navController.navigate(Screen.Profile.route)
            }
        )

        Column {
            Text(
                text = stringResource(id = R.string.meat),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.vegetable),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.spices),
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                },
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(text = stringResource(id = R.string.search))
            }
        }

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
fun CreateSearchScreen(navController: NavController) {
    SearchLayout(navController)
}

@Preview
@Composable
fun CreateSearchPreview() {

    val navController: NavController = rememberNavController()

    MatappTheme {
        CreateSearchScreen(navController = navController)
    }
}
