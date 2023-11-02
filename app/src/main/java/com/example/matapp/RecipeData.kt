package com.example.matapp

data class Recipe(
    val title: String = "",
    val cookTime: String = "",
    val difficulty: String = "",
    val ingredients: Map<String, Ingredient> = emptyMap(),
    val isVegan: Boolean = false,
    val spiceLevel: String = "",
    val userId: String = "",
    val imageResourceString: String = "picture_0"
)

data class Ingredient(
    val name: String = "",
    val quantity: String = "",
    val unit: String = ""
)