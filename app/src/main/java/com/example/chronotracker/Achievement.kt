package com.example.chronotracker

data class Achievement(
    val name: String,
    val description: String,
    var unlocked: Boolean = false
)
