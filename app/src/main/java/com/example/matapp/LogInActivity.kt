package com.example.matapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.matapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, ForYouActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnSuccessListener {
                                Toast.makeText(
                                    baseContext,
                                    "Account registered, please verify your email.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }?.addOnFailureListener {
                                Toast.makeText(
                                    baseContext,
                                    "Failed to send verification email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            when (task.exception) {
                                is FirebaseAuthUserCollisionException -> {
                                    Toast.makeText(
                                        baseContext,
                                        "This email is already in use",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        baseContext,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.resetPasswordButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val email = binding.emailEditText.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter an email address.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent successfully.")
                        Toast.makeText(
                            this,
                            "Password reset email sent!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Log.e(TAG, "Error sending password reset email.", task.exception)
                        Toast.makeText(
                            this,
                            "Error sending password reset email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user?.isEmailVerified == true) {
                                Toast.makeText(
                                    this,
                                    "Success!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, ForYouActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please verify your email before logging in.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Fail!: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

