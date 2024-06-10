package com.example.chronotracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.chronotracker.com.example.chronotracker.GoalsActivity



class Home : AppCompatActivity() {

    private lateinit var addCategory: Button

  //  private lateinit var collection: Button
    private lateinit var achievement: Button
    private lateinit var menu: Button

    private lateinit var notification: Button
    private lateinit var profile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        addCategory = findViewById(R.id.CategoryBtn)
        //capture = findViewById(R.id.captureBtn)
      ///  collection = findViewById(R.id.collectionBtn)
        achievement = findViewById(R.id.achievementBtn)
        menu = findViewById(R.id.menuBtn)
        //search = findViewById(R.id.searchBtn)
        notification = findViewById(R.id.notificationBtn)
        profile = findViewById(R.id.profileBtn)

        addCategory.setOnClickListener {
            val intent = Intent(this, AddCategory::class.java)
            startActivity(intent)
            finish()
        }

       /// capture.setOnClickListener {
       ///     val intent = Intent(this@Home, Capture::class.java)
       //     startActivity(intent)



       // collection.setOnClickListener {
         //   val intent = Intent(Intent(this@Home,ViewCollectionActivity::class.java))
         //   startActivity(intent)
       //     finish()
    //    }

        achievement.setOnClickListener {
            val intent = Intent(this@Home, GoalsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
