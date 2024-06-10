package com.example.chronotracker

import java.io.Serializable

    data class User(
        val userId: String,
        val email: String,
        val password:String,
        // Add more user properties here if needed
    ) : Serializable



