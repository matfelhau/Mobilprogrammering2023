package com.example.matapp

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast

class Utility  {
    companion object {
        fun showError(context: Context, message: String) {
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