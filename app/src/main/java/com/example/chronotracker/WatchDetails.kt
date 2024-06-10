package com.example.chronotracker

import android.net.Uri
import java.io.Serializable

data class Watch(
    val name: String,
    val color: String,
    val movement: String,
    val year: Int,
    val price: Double,
    val imageUri: Uri? // Added image URI field
) : Serializable