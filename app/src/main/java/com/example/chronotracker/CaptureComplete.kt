package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class CaptureCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_complete)

        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieAnimationView)
        lottieAnimationView.setAnimation("animation.json")
        lottieAnimationView.playAnimation()

        // Handler to delay the transition for 7 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to Watches activity after 7 seconds
            val intent = Intent(this, Watches::class.java)
            startActivity(intent)
            finish()
        }, 7000)
    }
}
