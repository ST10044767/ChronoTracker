package com.example.chronotracker.com.example.chronotracker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chronotracker.R
import com.google.firebase.firestore.FirebaseFirestore

class GoalsActivity : AppCompatActivity() {
    private lateinit var goalNumberInput: EditText
    private lateinit var goalWatchInput: EditText
    private lateinit var saveGoalButton: Button
    private lateinit var currentGoals: TextView
    private lateinit var firestore: FirebaseFirestore
    private var numWatchesOwned = 0 // Tracks the number of watches owned

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        goalNumberInput = findViewById(R.id.goal_number_input)
        goalWatchInput = findViewById(R.id.goal_watch_input)
        saveGoalButton = findViewById(R.id.save_goal_button)
        currentGoals = findViewById(R.id.current_goals)
        firestore = FirebaseFirestore.getInstance()

        // Load the number of watches owned from Firestore
        loadNumWatchesOwned()

        saveGoalButton.setOnClickListener {
            saveGoal()
        }

        loadCurrentGoals()
    }

    private fun saveGoal() {
        val goalNumber = goalNumberInput.text.toString().trim()
        val goalWatch = goalWatchInput.text.toString().trim()

        if (goalNumber.isNotEmpty() || goalWatch.isNotEmpty()) {
            val goalsMap = hashMapOf<String, Any>()
            if (goalNumber.isNotEmpty()) {
                goalsMap["numberGoal"] = goalNumber.toInt()
            }
            if (goalWatch.isNotEmpty()) {
                goalsMap["watchGoal"] = goalWatch
            }
            firestore.collection("goals")
                .add(goalsMap)
                .addOnSuccessListener {
                    goalNumberInput.text.clear()
                    goalWatchInput.text.clear()
                    Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
                    loadCurrentGoals()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to save goal", Toast.LENGTH_SHORT).show()
                    Log.e("GoalsActivity", "Error saving goal", exception)
                }
        } else {
            Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCurrentGoals() {
        var goalsText = "Your Current Goals:\n"
        firestore.collection("goals")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val numberGoal = document.getLong("numberGoal")?.toInt()
                    val watchGoal = document.getString("watchGoal")

                    if (numberGoal != null) {
                        val progress = calculateNumberGoalProgress(numberGoal)
                        goalsText += "Number of Watches: $numberGoal (Progress: $progress%)\n"
                    }
                    if (watchGoal != null) {
                        val isAchieved =
                            watchGoal == "any" || // Any watch goal is considered achieved if no specific watch is mentioned
                                    isSpecificWatchGoalAchieved(watchGoal)
                        goalsText += "Specific Watch Goal: $watchGoal (Achieved: $isAchieved)\n"
                    }
                }
                currentGoals.text = goalsText
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load goals", Toast.LENGTH_SHORT).show()
                Log.e("GoalsActivity", "Error loading goals", exception)
            }
    }

    fun calculateNumberGoalProgress(numberGoal: Int): Int {
        // calculate progress based on number of watches owned
        return (numWatchesOwned.toDouble() / numberGoal * 100).toInt()
    }

    fun loadNumWatchesOwned() {
        // Implement logic to load the number of watches owned from Firestore (or local storage)
        // This could involve querying a separate collection or fetching a field from the watch collection
        // For now, assume 0 watches are owned
        numWatchesOwned = 0
    }

    // isSpecificWatchGoalAchieved checks if a specific watch has been added to the collection
    // This function will likely require querying the watch collection in Firestore and potentially
    // filtering based on the watch goal name
    fun isSpecificWatchGoalAchieved(watchGoal: String): Boolean {
        // Implement logic to check if a watch matching the watchGoal name exists in the collection
        var isAchieved = false
        val watchRef = firestore.collection("watches")
        watchRef.whereEqualTo("name", watchGoal)
            .get()
            .addOnSuccessListener { result ->
                isAchieved = !result.isEmpty // If any documents are found, the goal is achieved
            }
            .addOnFailureListener { exception ->
                Log.e("GoalsActivity", "Error checking watch goal", exception)
            }

        return isAchieved
    }
}