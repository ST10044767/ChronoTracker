package com.example.chronotracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCategories extends Activity {

    // Declare member variables
    private EditText categoryEditText;
    private Button addCategoryButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        // Initialize UI elements
        categoryEditText = findViewById(R.id.categoryEditText);
        addCategoryButton = findViewById(R.id.addCategoryButton);

        // Get Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Implement click listener for add category button
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get category name from EditText
                String categoryName = categoryEditText.getText().toString().trim();

                // Input validation (optional)
                if (categoryName.isEmpty()) {
                    Toast.makeText(AddCategories.this, "Please enter a category name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add category to Firestore (replace with your actual logic)
                addCategoryToFirestore(categoryName);
            }
        });
    }

    // Method to add category to Firestore
    private void addCategoryToFirestore(String categoryName) {
        // Get a reference to the "categories" collection
        CollectionReference categoriesRef = firestore.collection("categories");

        // Create a new category document with a unique ID
        categoriesRef.add(new Category(categoryName))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddCategories.this, "Category added successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the EditText for new input
                    categoryEditText.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddCategories.this, "Error adding category: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Class to represent a category (optional, but improves data structure)
    public static class Category {
        private String name;

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
