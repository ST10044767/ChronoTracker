package com.example.chronotracker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WatchDetailsActivity : AppCompatActivity() {

    private lateinit var watchImage: ImageView
    private lateinit var watchName: TextView
    private lateinit var watchColor: TextView
    private lateinit var watchManufacturer: TextView
    private lateinit var watchYear: TextView
    private lateinit var watchPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)

        watchImage = findViewById(R.id.watch_image)
        watchName = findViewById(R.id.watch_name)
        watchColor = findViewById(R.id.watch_color)
        watchManufacturer = findViewById(R.id.watch_manufacturer)
        watchYear = findViewById(R.id.watch_year)
        watchPrice = findViewById(R.id.watch_price)

        val watchDetails = intent.getParcelableExtra<WatchDetails>("watchDetails")

        watchDetails?.let {
            watchName.text = it.name
            watchColor.text = it.color
            watchManufacturer.text = it.manufacturer
            watchYear.text = it.year
            watchPrice.text = it.price
            // Load image if available
        }
    }
}
