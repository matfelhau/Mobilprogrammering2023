package com.example.matapp.screens

import BottomNavBar
import Screen
import TopNavBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.R
import com.example.matapp.Recipe
import com.example.matapp.Utility
import com.example.matapp.model.ForYouViewModel
import com.google.firebase.auth.FirebaseAuth


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
    val recipes by viewModel.recipes.collectAsState()
    val currentRecipeIndex by viewModel.currentRecipeIndex.collectAsState()

    val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadRecipesFromFirebase()
    }

    val onNextRecipe = {
        Utility.showLogcatDebug("ForYouCompose: onNextRecipe function clicked")
        viewModel.getNextRecipe()
        Utility.showLogcatDebug("ForYouCompose: Current recipe index is: ${viewModel.currentRecipeIndex}")
    }

    val onSave = {
        viewModel.saveCurrentRecipe(userId.toString())
        Utility.showError(context, "Recipe saved!")
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
            recipes = recipes,
            onNextRecipe = onNextRecipe,
            onSave = onSave,
            currentRecipeIndex = currentRecipeIndex,
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
fun RecipeCard(recipe: Recipe, nextRecipe: () -> Unit, onSave: () -> Unit, refreshState: Int) {
    val customTextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    key(refreshState) {
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
                            style = customTextStyle,
                            color = Color.White
                        )

                        Text(
                            text = "Cook Time: ${recipe.cookTime} minutes",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "Difficulty: ${recipe.difficulty}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "Vegan: ${if (recipe.isVegan) "Yes" else "No"}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

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

                            IconButton(
                                onClick = {
                                    nextRecipe()
                                },
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
        var refreshState by remember { mutableIntStateOf(0) }
        RecipeCard(
            recipe = recipe,
            nextRecipe = {
                onNextRecipe()
                refreshState++
                Utility.showLogcatDebug("ForYouCompose: Refresh state is: $refreshState")
            },
            onSave = onSave,
            refreshState = refreshState
        )
    }
}






