package com.example.matapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matapp.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    sealed class UiState {
        object Success : UiState()
        object RegistrationSuccess : UiState()
        data class Error(val message: String) : UiState()
        object Loading : UiState()
        object Idle : UiState()
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (auth.currentUser?.isEmailVerified == true) {
                            _uiState.value = UiState.Success
                        } else {
                            auth.signOut()
                            _uiState.value = UiState.Error(task.exception?.message ?: Utility.VERIFY_EMAIL)
                        }
                    } else {
                        _uiState.value = UiState.Error(task.exception?.message ?: "Login failed!")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: Utility.ERROR_MESSAGE)
            }
        }
    }

    fun registerUser(email: String, password: String) {
        val database = FirebaseDatabase.getInstance().getReference("users")
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.sendEmailVerification()
                        user?.let {
                            val userId = it.uid
                            val userEmail = it.email ?: ""
                            val userData = mapOf("email" to userEmail)
                            database.child(userId).setValue(userData)
                        }
                        _uiState.value = UiState.RegistrationSuccess
                    } else {
                        _uiState.value = UiState.Error(task.exception?.message ?: "Registration failed!")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: Utility.ERROR_MESSAGE)
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        _uiState.value = UiState.Error(task.exception?.message ?: "Reset password failed!")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: Utility.ERROR_MESSAGE)
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }
}