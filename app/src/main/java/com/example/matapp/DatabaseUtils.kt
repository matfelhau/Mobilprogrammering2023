package com.example.matapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DatabaseUtils {
    private val database = FirebaseDatabase.getInstance().getReference("recipes")

    fun deleteEmptyRecipes() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (recipeSnapshot in snapshot.children) {
                    val title = recipeSnapshot.child("title").getValue(String::class.java)
                    if (title.isNullOrEmpty()) {
                        recipeSnapshot.ref.removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Utility.showLogcatError("Error in purging the database: ${error.message}")
            }
        })
    }
}