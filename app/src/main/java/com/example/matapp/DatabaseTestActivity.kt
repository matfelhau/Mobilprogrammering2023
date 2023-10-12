package com.example.matapp

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import com.example.matapp.databinding.ActivityDatabasetestBinding
import com.google.firebase.database.FirebaseDatabase

class DatabaseTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatabasetestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val database = FirebaseDatabase.getInstance()
        //val generalMyRef = database.reference
        val ingredientsMyRef = database.reference
            .child("meat")
            .child("beef stroganoff")
            .child("ingredients")


        Log.e("TAG", "Starting database mekking")

        ingredientsMyRef.get().addOnSuccessListener { dataSnapshot ->
            val ingredientsMap = dataSnapshot.value as? Map<String, String>
            val ingredientsList = ingredientsMap?.map { "${it.key}: ${it.value}" }

            if (ingredientsList != null) {
                val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, ingredientsList)
                binding.ingredientsListView.adapter = adapter
            }
            Log.i("firebase", "Got value $ingredientsList")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }
}