package com.example.matapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.matapp.databinding.ActivityForyouBinding

class ForYouActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityForyouBinding.inflate(layoutInflater)
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