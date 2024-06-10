package com.example.chronotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Retrieve email from intent (Login Activity)
        val userEmail = intent.getStringExtra("USER_EMAIL")

        // Set the user email as the user name
        val userName = findViewById<TextView>(R.id.user_name)
        userName.text = userEmail
    }
}