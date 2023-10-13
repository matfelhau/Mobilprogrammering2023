package com.example.matapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.matapp.databinding.ActivitySavedBinding
import com.example.matapp.databinding.ActivitySettingsBinding

import com.example.matapp.ui.theme.MatappTheme

class SavedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        val binding = ActivitySavedBinding.inflate(layoutInflater)
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

    }

}