package com.example.chronotracker

import android.widget.Toast

object AchievementRepository {
    private val achievements = listOf(
        Achievement("Starter", "Added the first watch to ChronoTracker!"),
        Achievement("Collector", "Added three watches to ChronoTracker!"),
        Achievement("Packrat", "Added ten watches to ChronoTracker!")
    )

    fun checkAchievements(watchCount: Int) {
        when(watchCount) {
            1 -> unlockAchievement("Starter")
            3 -> unlockAchievement("Collector")
            10 -> unlockAchievement("Packrat")
        }
    }

    private fun unlockAchievement(name: String) {
        val achievement = achievements.find {it.name == name}
        achievement?.let {
            it.unlocked = true
            showAchievementUnlocked(it)
        }
    }

    private fun showAchievementUnlocked(achievement: Achievement) {
        Toast.makeText(App.context, "Achievement Unlocked: ${achievement.name}", Toast.LENGTH_LONG).show()
    }
}