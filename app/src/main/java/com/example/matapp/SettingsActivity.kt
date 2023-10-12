package com.example.matapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.matapp.databinding.ActivitySettingsBinding

import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}