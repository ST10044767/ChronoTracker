package com.example.chronotracker

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class WatchDetailsActivity : AppCompatActivity() {

    private lateinit var watchImage: ImageView
    private lateinit var watchName: TextView
    private lateinit var watchColor: TextView
    private lateinit var watchMovement: TextView
    private lateinit var watchYear: TextView
    private lateinit var watchPrice: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)

        firestore = FirebaseFirestore.getInstance()

        watchImage = findViewById(R.id.watch_image)
        watchName = findViewById(R.id.watch_name)
        watchColor = findViewById(R.id.watch_color)
        watchMovement = findViewById(R.id.watch_movement)
        watchYear = findViewById(R.id.watch_year)
        watchPrice = findViewById(R.id.watch_price)

        val watchId = intent.getStringExtra("watches") ?: return
        val categoryName = intent.getStringExtra("categoryName") ?: return

        // Fetch watch details from Firestore
        firestore.collection("categories").document(categoryName).collection("watches").document(watchId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val watch = document.toObject(Watch::class.java)
                    watch?.let {
                        val imageUri = it.imageUri
                        if (!imageUri.isNullOrEmpty()) {
                            Picasso.get().load(Uri.parse(imageUri.toString())).into(watchImage)
                        } else {
                            // Set a placeholder image or handle the absence of image URI
                            watchImage.setImageResource(R.drawable.button_background) // Ensure placeholder_image exists in your drawable resources
                        }

                        watchName.text = "Name: ${it.name}"
                        watchColor.text = "Color: ${it.color}"
                        watchMovement.text = "Movement: ${it.movement}"
                        watchYear.text = "Year: ${it.year}"
                        watchPrice.text = "Price: $${it.price}"
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting document: $exception")
            }
    }
}

private fun Uri?.isNullOrEmpty(): Boolean {
    return this == null || this.toString().isEmpty()
}
