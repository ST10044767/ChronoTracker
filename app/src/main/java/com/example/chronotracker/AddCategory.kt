package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var backBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_categories)

        // Initialize the category list and adapter
        categoryList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList)

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance()

        //  ListView
        val categoryListView: ListView = findViewById(R.id.categoryListView)
        categoryListView.adapter = adapter

        // Initialize UI components
        addCategoryButton = findViewById(R.id.addCategoryButton)
        categoryEditText = findViewById(R.id.categoryEditText)
        backBtn=findViewById(R.id.backBtn3)

        // listener for the add category button
        addCategoryButton.setOnClickListener {
            val categoryName = categoryEditText.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                addCategory(categoryName)
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        //Back Button
        backBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        // Set item click listener for the ListView
        categoryListView.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categoryList[position]
            navigateToWatches(selectedCategory)
        }
    }

    override fun onResume() {
        super.onResume()
        // Load categories each time the activity resumes
        loadCategories()
    }

    private fun loadCategories() {
        firestore.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                categoryList.clear()
                for (document in result) {
                    document.getString("categoryName")?.let {
                        categoryList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load categories", Toast.LENGTH_SHORT).show()
                Log.e("AddCategory", "Error loading categories", exception)
            }
    }

    private fun addCategory(categoryName: String) {
        val categoryDocument = firestore.collection("categories").document()
        categoryDocument.set(mapOf("categoryName" to categoryName))
            .addOnSuccessListener {
                categoryList.add(categoryName)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show()
                categoryEditText.text.clear()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show()
                Log.e("AddCategory", "Error adding category", exception)
            }
    }

    private fun navigateToWatches(categoryName: String) {
        val intent = Intent(this, Watches::class.java)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }
}
