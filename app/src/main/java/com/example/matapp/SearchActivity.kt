package com.example.matapp

import android.os.Bundle
import androidx.activity.ComponentActivity

import com.example.matapp.ui.theme.MatappTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}