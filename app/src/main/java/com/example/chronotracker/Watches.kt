package com.example.chronotracker


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class Watches : AppCompatActivity() {

    private lateinit var watchList: ArrayList<DocumentSnapshot>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var selectedCategory: String
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watches)


        selectedCategory = intent.getStringExtra("category").toString()
        firestore = FirebaseFirestore.getInstance()

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
                    adapter.add(watchName)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                println("Error getting documents: $exception")
            }

        watchListView.setOnItemClickListener { _, _, position, _ ->
            val selectedWatch = watchList[position]
            val intent = Intent(this, WatchDetailsActivity::class.java)
            intent.putExtra("watchId", selectedWatch.id)
            startActivity(intent)
        }
    }
}