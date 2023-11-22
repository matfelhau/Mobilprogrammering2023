package com.example.matapp.screens

import BottomNavBar
import Screen
import SearchViewModel
import TopNavBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.R
import com.example.matapp.Utility
import com.example.matapp.ui.theme.MatappTheme

class SearchCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            SearchLayout(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayout(navController: NavController) {
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val searchViewModel: SearchViewModel = viewModel()
    val searchResult = searchViewModel.searchResults.collectAsState()

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

        TextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                searchViewModel.searchRecipes(it)
            },
            label = { Text(text = "Search for recipes") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        Column {
            searchResult.value.forEach { recipe ->
                Text(text = recipe.title, modifier = Modifier.padding(8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                          Utility.showMessage(context = context, "Coming soon!")
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
