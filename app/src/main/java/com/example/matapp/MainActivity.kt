package com.example.matapp

import CreateRecipeLayout
import LoginScreen
import ProfilePictureLayout
import SavedRecipesViewModel
import Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.matapp.model.ForYouViewModel
import com.example.matapp.screens.ForYouLayout
import com.example.matapp.screens.SavedLayout
import com.example.matapp.screens.SearchLayout
import com.example.matapp.screens.SettingsLayout
import com.example.matapp.ui.theme.MatappTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var recipeId: String
    private val database by lazy { FirebaseDatabase.getInstance().getReference("recipes") }
    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    private val viewModel: ForYouViewModel by viewModels()
    private val savedRecipesViewModel: SavedRecipesViewModel by viewModels()

    private fun initializeRecipe() {
        val initialRecipeData = mapOf(
            "userId" to userId,
            "title" to "",
            "cookTime" to "",
            "difficulty" to "",
            "spiceLevel" to ""
        )
        database.child(recipeId).setValue(initialRecipeData)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        recipeId = database.push().key.toString()

        initializeRecipe()

        super.onCreate(savedInstanceState)
        setContent {
            MatappTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        LoginScreen(navController)
                    }
                    composable(Screen.ForYou.route) {
                        ForYouLayout(navController, viewModel = viewModel)
                    }
                    composable(Screen.Search.route) {
                        SearchLayout(navController)
                    }
                    composable(Screen.Saved.route) {
                        SavedLayout(navController, savedRecipesViewModel)
                    }
                    composable(Screen.Settings.route) {
                        SettingsLayout(navController)
                    }
                    composable(Screen.Create.route) {
                        CreateRecipeLayout(navController, recipeId, userId, database)
                    }
                    composable(Screen.Profile.route) {
                        ProfilePictureLayout(navController)
                    }
                }
            }
        }
    }
}
