package com.example.matapp.model

import androidx.lifecycle.ViewModel
import com.example.matapp.Recipe
import com.example.matapp.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SavedRecipesViewModel : ViewModel() {
    private val database by lazy { FirebaseDatabase.getInstance().getReference("users") }
    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes: StateFlow<List<Recipe>> = _savedRecipes

    fun loadSavedRecipesFromFirebase() {

        userId?.let { uid ->
            val userRecipesRef = database.child(uid).child("saved_recipes")

            userRecipesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val savedRecipes = mutableListOf<Recipe>()

                    for (recipeSnapshot in snapshot.children) {
                        val recipe = recipeSnapshot.getValue(Recipe::class.java)
                        recipe?.let { savedRecipes.add(it) }
                    }

                    _savedRecipes.value = savedRecipes
                }

                override fun onCancelled(error: DatabaseError) {
                    Utility.showLogcatError("Can't import data from database: ${error.message}")
                }
            })
        }
    }


    fun deleteRecipe(recipe: Recipe) {
        userId?.let { uid ->
            val userRecipeRef = database.child(uid).child("saved_recipes")
            userRecipeRef.child(recipe.recipeId).removeValue()
        }
    }
}
