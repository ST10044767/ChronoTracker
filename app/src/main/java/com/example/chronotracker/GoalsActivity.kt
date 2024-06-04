package com.example.chronotracker.com.example.chronotracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chronotracker.R

class GoalsActivity : AppCompatActivity() {
    private lateinit var goalNumberInput: EditText
    private lateinit var goalWatchInput: EditText
    private lateinit var saveGoalButton: Button
    private lateinit var currentGoals: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        goalNumberInput = findViewById(R.id.goal_number_input)
        goalWatchInput = findViewById(R.id.goal_watch_input)
        saveGoalButton = findViewById(R.id.save_goal_button)
        currentGoals = findViewById(R.id.current_goals)

        saveGoalButton.setOnClickListener {
            saveGoal()
        }

        loadCurrentGoals()
    }

    private fun saveGoal() {
        val goalNumber = goalNumberInput.text.toString()
        val goalWatch = goalWatchInput.text.toString()

        if (goalNumber.isNotEmpty() || goalWatch.isNotEmpty()) {
            // Save goal to database
            // Example: DatabaseHelper.saveGoal(goalNumber, goalWatch)
            Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
            loadCurrentGoals()
        } else {
            Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCurrentGoals() {
        // Load current goals from the database and display them
        // Example: val goals = DatabaseHelper.getGoals()
        var goalsText = "Your Current Goals:\n"

        // Example:
        // for (goal in goals) {
        //     goalsText += "${goal}\n"
        // }

        currentGoals.text = goalsText
    }
}
