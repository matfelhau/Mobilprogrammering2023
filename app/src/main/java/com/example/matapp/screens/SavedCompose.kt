package com.example.matapp.screens

import BottomNavBar
import Screen
import TopNavBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.model.SavedRecipesViewModel
import com.example.matapp.ui.theme.MatappTheme
import com.example.matapp.utility.Recipe
import com.example.matapp.utility.Utility

class SavedCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val savedRecipesViewModel = SavedRecipesViewModel()
            SavedLayout(
                navController = navController,
                savedRecipesViewModel = savedRecipesViewModel)
        }
    }
}

@Composable
fun SavedLayout(navController: NavController, savedRecipesViewModel: SavedRecipesViewModel) {
    val context = LocalContext.current
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
                SavedRecipeItem(recipe = recipe, onDeleteClick = {
                    savedRecipesViewModel.deleteRecipe(recipe)
                    Utility.showMessage(context, "Recipe deleted")
                })
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
fun SavedRecipeItem(recipe: Recipe, onDeleteClick: () -> Unit) {
    val context = LocalContext.current
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

            Row {
                Button(
                    onClick = {
                        Utility.showMessage(context = context, "Coming soon!")
                    }
                ) {
                    Text(text = "View Recipe")
                }

                Spacer(modifier = Modifier.width(200.dp))

                IconButton(
                    onClick = onDeleteClick,
                    content = {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete saved recipe")
                    }
                )
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