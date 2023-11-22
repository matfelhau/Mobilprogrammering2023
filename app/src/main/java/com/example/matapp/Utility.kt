package com.example.matapp

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast

class Utility  {
    companion object {
        const val VERIFY_EMAIL = "Please verify your email address."
        const val ERROR_SENDING_RESET_MAIL = "Error sending password reset email."
        const val RESET_MAIL_MESSAGE = "Password reset email sent!."
        const val ERROR_MESSAGE = "ERROR!"
        const val CHECK_EMAIL = "Please check your email."
        const val ENTER_EMAIL = "Please enter an email address."
        const val ENTER_EMAIL_AND_PASSWORD = "Please enter email and password!."
        const val USER_FEEDBACK = "User registered."
        const val INPUT_FEEDBACK = "Fill out the fields."

        fun showMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun showLogcatError(message: String) {
            Log.e(TAG, message)
        }

        fun showLogcatDebug(message: String) {
            Log.d(TAG, message)
        }

        fun showLogcatWarning(message: String) {
            Log.w(TAG, message)
        }
    }
}