package com.example.matapp.screens

import BottomNavBar
import Screen
import TopNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth



@Composable
fun ShowRecipeLayout(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    val customTextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,

        )

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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
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
                            //text = recipe.title,
                            text = "Hotdog",
                            style = customTextStyle,
                            color = Color.White
                        )

                        Text(
                            //text = "Cook Time: ${recipe.cookTime} minutes",
                            text = "Cook Time: 8 min",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            //text = "Difficulty: ${recipe.difficulty}",
                            text = "Difficulty: Easy",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            //text = "Vegan: ${if (recipe.isVegan) "Yes" else "No"}",
                            text = "Vegan: No",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            //text = "Spice Level: ${recipe.spiceLevel}",
                            text = "Spice Level: Mild",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "Ingredients:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                    }
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





/*
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

*/