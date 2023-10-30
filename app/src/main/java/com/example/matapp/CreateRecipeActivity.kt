package com.example.matapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.example.matapp.databinding.ActivityCreaterecipeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateRecipeActivity : ComponentActivity() {

    private var dataAdded = false
    private lateinit var recipeId: String
    private val database by lazy { FirebaseDatabase.getInstance().getReference("recipes") }
    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeId = database.push().key ?: return showError("Error initializing recipe.")
        initializeRecipe()

        val binding = ActivityCreaterecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

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

    private fun setupUI(binding: ActivityCreaterecipeBinding, navController: NavController) {
        binding.imageButtonSearch.setOnClickListener {
            navController.navigate(Screen.ForYou.route)
        }

        binding.imageButtonSaved.setOnClickListener {
            navController.navigate(Screen.ForYou.route)
        }

        binding.imageButtonForYou.setOnClickListener {
            navController.navigate(Screen.ForYou.route)
        }

        binding.imageButtonSettings.setOnClickListener {
            navController.navigate(Screen.ForYou.route)
        }

        val difficultyOptions = arrayOf("Easy", "Medium", "Hard")
        val spiceOptions = arrayOf("Mild", "Medium", "Strong")
        val measuringUnitOptions = arrayOf("grams", "dl", "tsp", "tbsp", "slices", "pcs")

        val spinnerDifficulty: Spinner = binding.spinnerRecipeDifficulty
        val spinnerSpice: Spinner = binding.spinnerSpiceLevel
        val spinnerQuantity: Spinner = binding.spinnerIngredientQuantity

        val difficultyAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyOptions)
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spiceOptions)
        spiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterQuantity =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, measuringUnitOptions)
        adapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSpice.adapter = spiceAdapter
        spinnerDifficulty.adapter = difficultyAdapter
        spinnerQuantity.adapter = adapterQuantity


        binding.addIngredientButton.setOnClickListener {
            addIngredient(binding)
        }

        binding.createRecipeButton.setOnClickListener {
            createRecipe(binding)
        }
    }

    private fun addIngredient(binding: ActivityCreaterecipeBinding) {
        val ingredient = binding.editTextRecipeIngredients.text.toString()
        val quantity = binding.editTextIngredientQuantity.text.toString()
        val measuringUnit = binding.spinnerIngredientQuantity.selectedItem.toString()

        if (ingredient.isEmpty() || quantity.isEmpty()) {
            showError("Please fill out all ingredient fields.")
            return
        }

        val ingredientData = mapOf(
            "name" to ingredient,
            "quantity" to quantity,
            "unit" to measuringUnit,
        )

        recipeId?.let {
            database.child(it).child("ingredients").push().setValue(ingredientData)
                .addOnSuccessListener {
                    Log.d("FirebaseDebug", "Ingredient added successfully!")
                    binding.editTextRecipeIngredients.text.clear()
                    binding.editTextIngredientQuantity.text.clear()
                    dataAdded = true
                }.addOnFailureListener { exception ->
                    Log.e("FirebaseDebug", "Error adding ingredient: ", exception)
                }
        } ?: showError("Error adding ingredient.")
    }

    private fun createRecipe(binding: ActivityCreaterecipeBinding) {
        val recipeTitle = binding.editTextRecipeTitle.text.toString()
        if (recipeTitle.isEmpty()) {
            showError("Please enter a title for your recipe.")
            return
        }
        val cookTime = binding.editTextRecipeCookTime.text.toString()
        if (cookTime.isEmpty()) {
            showError("Please enter a cook time for your recipe.")
        }

        val difficulty = binding.spinnerRecipeDifficulty.selectedItem.toString()
        if (difficulty.isEmpty()) {
            showError("Please enter a difficulty for your recipe.")
        }
        val spiceLevel = binding.spinnerSpiceLevel.selectedItem.toString()
        if (spiceLevel.isEmpty()) {
            showError("Please enter a spice level for your recipe.")
        }

        val isVegan = binding.veganCheckbox.isChecked
        val allergens = mapOf(
            "gluten" to binding.glutenCheckbox.isChecked,
            "nuts" to binding.nutsCheckbox.isChecked,
            "soy" to binding.soyCheckbox.isChecked
        )
        val recipeData = mapOf(
            "userId" to userId,
            "title" to recipeTitle,
            "cookTime" to cookTime,
            "difficulty" to difficulty,
            "spiceLevel" to spiceLevel,
            "isVegan" to isVegan,
            "allergens" to allergens
        )

        database.child(recipeId!!).updateChildren(recipeData).addOnSuccessListener {
            Log.d("FirebaseDebug", "Data written successfully!")
            dataAdded = true
        }.addOnFailureListener { exception ->
            Log.e("FirebaseDebug", "Error writing data: ", exception)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (!dataAdded) {
            database.child(recipeId).removeValue()
        }
        super.onBackPressed()
    }
}

