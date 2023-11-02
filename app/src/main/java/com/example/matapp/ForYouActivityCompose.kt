package com.example.matapp

import BottomNavBar
import ForYouViewModel
import TopNavBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.platform.LocalContext


class ForYouActivityCompose : AppCompatActivity() {
    private val viewModel: ForYouViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            ForYouLayout(navController = navController, viewModel = viewModel)

        }
    }
}

@Composable
fun ForYouLayout(navController: NavController, viewModel: ForYouViewModel) {
    val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    LaunchedEffect(Unit) {
        viewModel.loadRecipesFromFirebase()
    }

    val onNextRecipe = {
        val nextRecipe = viewModel.getNextRecipe()
        if (nextRecipe != null) {
            viewModel.incrementCurrentRecipeIndex()
        }
    }

    val onSave = {
        viewModel.saveCurrentRecipe(userId.toString())
    }

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

        RecipeCardStack(
            recipes = viewModel.recipes,
            onNextRecipe = onNextRecipe,
            onSave = onSave,
            currentRecipeIndex = viewModel.currentRecipeIndex
        )




        Spacer(modifier = Modifier.height(16.dp))



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
fun RecipeCard(recipe: Recipe, onNextRecipe: () -> Unit, onSave: () -> Unit) {
    val context = LocalContext.current

    val customTextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray.copy(alpha = 0.6f)) //
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = recipe.title,
                        style = customTextStyle, // Use the custom text style here
                        color = Color.White
                    )

                    // Display cook time
                    Text(
                        text = "Cook Time: ${recipe.cookTime} minutes",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )

                    // Display difficulty
                    Text(
                        text = "Difficulty: ${recipe.difficulty}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )

                    // Display whether it's vegan
                    Text(
                        text = "Vegan: ${if (recipe.isVegan) "Yes" else "No"}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )

                    // Display spice level
                    Text(
                        text = "Spice Level: ${recipe.spiceLevel}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Add more recipe details here

                        IconButton(
                            onClick = { onNextRecipe() },
                            modifier = Modifier.size(48.dp),
                            content = {
                                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Recipe")
                            }
                        )

                        IconButton(
                            onClick = { onSave() },
                            modifier = Modifier.size(48.dp),
                            content = {
                                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Save Recipe")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeCardStack(
    recipes: List<Recipe>,
    onNextRecipe: () -> Unit,
    onSave: () -> Unit,
    currentRecipeIndex: Int
) {
    val currentRecipe = recipes.getOrNull(currentRecipeIndex)

    currentRecipe?.let { recipe ->
        RecipeCard(
            recipe = currentRecipe,
            onNextRecipe = {
                onNextRecipe()
            },
            onSave = onSave
        )
    }
}






