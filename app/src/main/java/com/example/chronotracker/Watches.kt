package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Watches : AppCompatActivity() {

    private lateinit var watchList: ArrayList<DocumentSnapshot>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var selectedCategory: String
    private lateinit var addWatchButton: Button
    private lateinit var firestore: FirebaseFirestore

    private var watchCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watches)

        // Initialize UI components
        addWatchButton = findViewById(R.id.addWatchButton)
        addWatchButton.setOnClickListener {
            //Adding watch count for achievement system
            watchCount++
            AchievementRepository.checkAchievements(watchCount)

            val intent = Intent(this, Capture::class.java)
            intent.putExtra("categoryName", selectedCategory)
            startActivity(intent)
        }

        // Get the selected category from the intent
        selectedCategory = intent.getStringExtra("categoryName").toString()

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance()

        // Initialize the ListView and adapter
        val watchListView: ListView = findViewById(R.id.watchListView)
        watchList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        watchListView.adapter = adapter

        // Fetch watches for the selected category from Firestore
        firestore.collection("categories").document(selectedCategory).collection("watches")
            .get()
            .addOnSuccessListener { documents ->
                watchList.clear()
                adapter.clear()
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
            intent.putExtra("watches", selectedWatch.id)
            startActivity(intent)
        }
    }
}
