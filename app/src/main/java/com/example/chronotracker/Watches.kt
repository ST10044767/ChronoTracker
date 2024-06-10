package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Watches : AppCompatActivity() {

    private lateinit var watchList: ArrayList<DocumentSnapshot>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var selectedCategory: String
    private lateinit var addWatchButton: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watches)

        // Initialize UI components
        addWatchButton = findViewById(R.id.addWatchButton)
        addWatchButton.setOnClickListener {
            val intent1 = Intent(this, Capture::class.java)
            startActivity(intent1)
            finish()
        }

        // Get the selected category from the intent
        selectedCategory = intent.getStringExtra("category").toString()

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance()

        // Initialize the ListView and adapter
        val watchListView: ListView = findViewById(R.id.watchListView)
        watchList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        watchListView.adapter = adapter

        // Fetch watches for the selected category from Firestore
        firestore.collection("watches")
            .whereEqualTo("category", selectedCategory)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    watchList.add(document)
                    val watchName = document.getString("name")
                    if (watchName != null) {
                        adapter.add(watchName)
                    }
                }
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                println("Error getting documents: $exception")
            }

        // Set an item click listener to navigate to watch details
        watchListView.setOnItemClickListener { _, _, position, _ ->
            val selectedWatch = watchList[position]
            val intent = Intent(this, WatchDetailsActivity::class.java)
            intent.putExtra("watchId", selectedWatch.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Load categories each time the activity resumes
        loadWatches()
    }
private fun loadWatches() {
    firestore.collection("watches")
        .whereEqualTo("category", selectedCategory)
        .get()
        .addOnSuccessListener { documents ->
            watchList.clear()
            for (document in documents) {
                watchList.add(document)
                document.getString("name")?.let {
                    adapter.add(it)
                }
            }
            adapter.notifyDataSetChanged()
        }
        .addOnFailureListener { exception ->
            Toast.makeText(this, "Failed to load watches", Toast.LENGTH_SHORT).show()
            Log.e("Watches", "Error loading watches", exception)
        }
}
}
