package com.example.matapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.matapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Logged in, navigate to next activity.
        }

        // Set up any listeners or other configurations for your views here
        // For example, you can set up a click listener for the login button
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Fail!: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
