package com.example.chronotracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class CaptureDetailsActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView
    private lateinit var editName: EditText
    private lateinit var editColor: EditText
    private lateinit var editManufacturer: EditText
    private lateinit var editYear: EditText
    private lateinit var editPrice: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_details)

        imagePreview = findViewById(R.id.image_preview)
        editName = findViewById(R.id.edit_name)
        editColor = findViewById(R.id.edit_color)
        editManufacturer = findViewById(R.id.edit_movement)
        editYear = findViewById(R.id.edit_year)
        editPrice = findViewById(R.id.edit_price)
        buttonSubmit = findViewById(R.id.button_submit)

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        imageUri?.let {
            imagePreview.setImageURI(it)
        }

        buttonSubmit.setOnClickListener {
            val watchDetails = WatchDetails(
                name = editName.text.toString(),
                color = editColor.text.toString(),
                manufacturer = editManufacturer.text.toString(),
                year = editYear.text.toString(),
                price = editPrice.text.toString()
            )

            val intent = Intent(this,CaptureCompleteActivity::class.java)
            intent.putExtra("watchDetails", watchDetails)
            startActivity(intent)
        }
    }
}
