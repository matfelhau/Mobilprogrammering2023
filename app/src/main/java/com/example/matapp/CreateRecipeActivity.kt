package com.example.matapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        val options = arrayOf( 1, 2, 3)

        val spinnerDifficulty: Spinner = findViewById(R.id.spinnerRecipeDifficulty)
        val spinnerSpice: Spinner = findViewById(R.id.spinnerSpiceLevel)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSpice.adapter = adapter
        spinnerDifficulty.adapter = adapter

    }
}