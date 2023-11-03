package com.example.matapp

import BottomNavBar
import SavedRecipesViewModel
import Screen
import TopNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.ui.theme.MatappTheme

@Composable
fun SavedLayout(navController: NavController, savedRecipesViewModel: SavedRecipesViewModel) {
    LaunchedEffect(Unit) {
        savedRecipesViewModel.loadSavedRecipesFromFirebase()
    }

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

        LazyColumn {
            items(savedRecipesViewModel.savedRecipes.value) { recipe ->
                SavedRecipeItem(recipe = recipe)
            }
        }
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
fun SavedRecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = recipe.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cook Time: ${recipe.cookTime}",
                style = TextStyle(fontSize = 14.sp)
            )

            Text(
                text = "Difficulty: ${recipe.difficulty}",
                style = TextStyle(fontSize = 14.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    //TO DO Implement logic to view the recipe or perform actions
                }
            ) {
                Text(text = "View Recipe")
            }
        }
    }
}


@Composable
fun CreateSavedScreen(navController: NavController, savedRecipesViewModel: SavedRecipesViewModel) {

    SavedLayout(navController, savedRecipesViewModel)
}

@Preview
@Composable
fun CreateSavedPreview() {
    val navController: NavController = rememberNavController()
    val savedRecipesViewModel = SavedRecipesViewModel()

    MatappTheme {
        CreateSavedScreen(navController = navController, savedRecipesViewModel = savedRecipesViewModel)
    }
}