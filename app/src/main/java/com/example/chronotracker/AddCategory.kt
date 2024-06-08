package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddCategory : AppCompatActivity() {

    private lateinit var categoryList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var categoryEditText: EditText
    private lateinit var addCategoryButton: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_categories)

        categoryList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList)
        firestore = FirebaseFirestore.getInstance()

        val categoryListView: ListView = findViewById(R.id.categoryListView)
        categoryListView.adapter = adapter

        addCategoryButton = findViewById(R.id.addCategoryButton)
        categoryEditText = findViewById(R.id.categoryEditText)

        addCategoryButton.setOnClickListener {
            val categoryName = categoryEditText.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                // Add category to Firestore
                firestore.collection("categories")
                    .add(mapOf("name" to categoryName))
                    .addOnSuccessListener {
                        Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show()
                    }
                categoryEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
        }

        categoryListView.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categoryList[position]
            val intent = Intent(this, Watches::class.java)
            intent.putExtra("category", selectedCategory)
            startActivity(intent)
        }
    }
}
