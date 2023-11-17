package com.example.matapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateRecipeViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().getReference("recipes")
    private val _recipeUpdateStatus = MutableStateFlow<String?>(null)
    fun initializeRecipe(userId: String?, recipeId: String) {
        val initialRecipeData = mapOf(
            "userId" to userId,
            "title" to "",
            "cookTime" to "",
            "difficulty" to "",
            "spiceLevel" to ""
        )
        database.child(recipeId).setValue(initialRecipeData)
    }

    fun addIngredient(recipeId: String, ingredient: String, quantity: String, measuringUnit: String) {
        viewModelScope.launch {
            val ingredientData = mapOf(
                "name" to ingredient,
                "quantity" to quantity,
                "unit" to measuringUnit
            )
            database.child(recipeId).child("ingredients").push().setValue(ingredientData)
                .addOnSuccessListener {
                    _recipeUpdateStatus.value = "Ingredient added successfully"
                }.addOnFailureListener {
                    _recipeUpdateStatus.value = "Failed to add ingredient"
                }
        }
    }

    fun createRecipeAndAddToDatabase(
        recipeId: String,
        userId: String?,
        title: String,
        cookTime: String,
        difficulty: String,
        spiceLevel: String,
        isVegan: Boolean,
        allergens: Map<String, Boolean>
    ) {
        val recipeData = mapOf(
            "userId" to userId,
            "title" to title,
            "cookTime" to cookTime,
            "difficulty" to difficulty,
            "spiceLevel" to spiceLevel,
            "isVegan" to isVegan,
            "allergens" to allergens
        )

        database.child(recipeId).updateChildren(recipeData)
            .addOnSuccessListener {
                _recipeUpdateStatus.value = "Recipe created successfully!"
            }
            .addOnFailureListener { exception ->
                _recipeUpdateStatus.value = "Failed to create recipe: ${exception.message}"
            }
    }
}