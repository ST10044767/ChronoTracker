package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class CaptureCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_complete)

        // Display animation or image
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate back to HomeActivity after 7 seconds
            val intent = Intent(this, Watches::class.java)
            startActivity(intent)
            finish()
        }, 7000)
    }
}
