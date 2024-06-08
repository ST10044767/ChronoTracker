package com.example.chronotracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CaptureDetails : AppCompatActivity() {

    private lateinit var imagePreview: ImageView
    private lateinit var editName: EditText
    private lateinit var editColor: EditText
    private lateinit var editMovement: EditText
    private lateinit var editYear: EditText
    private lateinit var editPrice: EditText
    private lateinit var buttonSubmit: Button

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_details)

        imagePreview = findViewById(R.id.image_preview)
        editName = findViewById(R.id.edit_name)
        editColor = findViewById(R.id.edit_color)
        editMovement = findViewById(R.id.edit_movement)
        editYear = findViewById(R.id.edit_year)
        editPrice = findViewById(R.id.edit_price)
        buttonSubmit = findViewById(R.id.button_submit)

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        imageUri?.let {
            imagePreview.setImageURI(it)
        }

        buttonSubmit.setOnClickListener {
            val name = editName.text.toString()
            val color = editColor.text.toString()
            val movement = editMovement.text.toString()
            val yearString = editYear.text.toString()
            val priceString = editPrice.text.toString()

            if (name.isBlank() || color.isBlank() || movement.isBlank() || yearString.isBlank() || priceString.isBlank()) {
                Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val year = yearString.toIntOrNull()
            val price = priceString.toDoubleOrNull()

            if (year == null || price == null) {
                Toast.makeText(this, "Year must be an integer and Price must be a valid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val watch = Watch(
                name = name,
                color = color,
                movement = movement,
                year = year,
                price = price,
                imageUri = imageUri
            )

            saveWatchToFirestore(watch)
        }
    }

    private fun saveWatchToFirestore(watch: Watch) {
        val category = intent.getStringExtra("name") ?: run {
            Toast.makeText(this, "Category is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val watchMap = hashMapOf(
            "name" to watch.name,
            "color" to watch.color,
            "movement" to watch.movement,
            "year" to watch.year,
            "price" to watch.price,
            "imageUri" to watch.imageUri?.toString()
        )

        db.collection("categories").document(category).collection("watches").add(watchMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Watch saved successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CaptureCompleteActivity::class.java)
                intent.putExtra("Watch", watch)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving watch: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
