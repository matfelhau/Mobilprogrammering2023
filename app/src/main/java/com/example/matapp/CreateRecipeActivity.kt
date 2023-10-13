package com.example.matapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.matapp.databinding.ActivityCreaterecipeBinding

class CreateRecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCreaterecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageButtonSaved.setOnClickListener {
            val intent = Intent(this, SavedActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageButtonForYou.setOnClickListener {
            val intent = Intent(this, ForYouActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageButtonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val difficultyOptions = arrayOf("Easy", "Medium", "Hard")
        val spiceOptions = arrayOf("Mild" , "Medium", "Strong")

        val spinnerDifficulty: Spinner = findViewById(R.id.spinnerRecipeDifficulty)
        val spinnerSpice: Spinner = findViewById(R.id.spinnerSpiceLevel)

        val difficultyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyOptions)
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spiceOptions)
        spiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSpice.adapter = spiceAdapter
        spinnerDifficulty.adapter = difficultyAdapter

        binding.createRecipeButton.setOnClickListener {
            val recipeTitle = binding.editTextRecipeTitle.text.toString()
            if (recipeTitle.isEmpty()) {
                Toast.makeText(this, "Please enter a title for your recipe.", Toast.LENGTH_SHORT).show()
            }
            val cookTime = binding.editTextRecipeCookTime.text.toString()
            if (cookTime.isEmpty()) {
                Toast.makeText(this, "Please enter a cooktime for your recipe.", Toast.LENGTH_SHORT).show()
            }
            val ingredient = binding.editTextRecipeIngredients.text.toString()
            if (ingredient.isEmpty()) {
                Toast.makeText(this, "Please enter a ingredient for you recipe.", Toast.LENGTH_SHORT).show()
            }
            //add quantity input to specify to the database
            val difficulty = binding.spinnerRecipeDifficulty.selectedItem.toString()
            if (difficulty.isEmpty()) {
                Toast.makeText(this, "Please enter a difficulty for your recipe.", Toast.LENGTH_SHORT).show()
            }
            val spiceLevel = binding.spinnerSpiceLevel.selectedItem.toString()
            if (spiceLevel.isEmpty()) {
                Toast.makeText(this, "Please enter a spice level for your recipe.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
