package com.example.matapp.model

import androidx.lifecycle.ViewModel
import com.example.matapp.Recipe
import com.example.matapp.Utility
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForYouViewModel : ViewModel() {
    private val _currentRecipeIndex = MutableStateFlow(0)
    val currentRecipeIndex: StateFlow<Int> = _currentRecipeIndex

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

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
                _recipes.value = fetchedRecipes
                Utility.showLogcatDebug("Fetched ${fetchedRecipes.size} recipes")
            }
            override fun onCancelled(error: DatabaseError) {
                Utility.showLogcatError("Firebase data loading cancelled: $error")
            }
        })
    }

    fun getNextRecipe(): Recipe? {
        if (_recipes.value.isEmpty()) return null

        val newIndex = (_currentRecipeIndex.value + 1) % _recipes.value.size
        _currentRecipeIndex.value = newIndex
        return _recipes.value[newIndex]
    }

    fun saveCurrentRecipe(userId: String) {
        val currentRecipe = _recipes.value?.getOrNull(_currentRecipeIndex.value!!)
        if (currentRecipe != null) {
            val userRecipesRef = database.child("users").child(userId).child("saved_recipes")

            val recipeId = userRecipesRef.push().key
            if (recipeId != null) {
                userRecipesRef.child(recipeId).setValue(currentRecipe)
            }
        }
    }
}