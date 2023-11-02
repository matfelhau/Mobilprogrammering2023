package com.example.matapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForYouViewModel : ViewModel() {
    var currentRecipeIndex = 0
    var recipes: List<Recipe> = emptyList()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun loadRecipesFromFirebase() {
        val recipesRef: DatabaseReference = database.child("recipes")

        recipesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedRecipes = mutableListOf<Recipe>()

                for (recipeSnapshot in snapshot.children) {
                    val recipe = recipeSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        fetchedRecipes.add(recipe)
                    }
                }
                recipes = fetchedRecipes
                Log.d("com.example.matapp.ForYouViewModel", "Fetched ${fetchedRecipes.size} recipes")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("com.example.matapp.ForYouViewModel", "Firebase data loading cancelled: $error")
            }
        })
    }

    fun getNextRecipe(): Recipe? {
        if (recipes.isEmpty()) return null

        val newIndex = (currentRecipeIndex + 1) % recipes.size
        currentRecipeIndex = newIndex
        return recipes[newIndex]
    }

    fun saveCurrentRecipe(userId: String) {
        val currentRecipe = recipes.getOrNull(currentRecipeIndex)
        if (currentRecipe != null) {
            val userRecipesRef = database.child("users").child(userId).child("saved_recipes")
            val recipeId = userRecipesRef.push().key
            if (recipeId != null) {
                userRecipesRef.child(recipeId).setValue(currentRecipe)
            }
        }
    }

    fun incrementCurrentRecipeIndex() {
        if (recipes.isNotEmpty()) {
            val newIndex = (currentRecipeIndex + 1) % recipes.size
            currentRecipeIndex = newIndex
        }
    }
}