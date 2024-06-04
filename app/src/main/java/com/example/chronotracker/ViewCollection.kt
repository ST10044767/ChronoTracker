package com.example.chronotracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class ViewCollectionActivity : AppCompatActivity() {

    private val watchList = mutableListOf<WatchDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collection)

        val watchListView: ListView = findViewById(R.id.watch_list)
        val watchAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, watchList.map { it.name })
        watchListView.adapter = watchAdapter

        watchListView.setOnItemClickListener { _, _, position, _ ->
            val selectedWatch = watchList[position]
            val intent = Intent(this, WatchDetailsActivity::class.java)
            intent.putExtra("watchDetails", selectedWatch)
            startActivity(intent)
        }

        // Add new watch details if passed from CaptureDetailsActivity
        intent.getParcelableExtra<WatchDetails>("watchDetails")?.let {
            watchList.add(it)
            watchAdapter.notifyDataSetChanged()
        }
    }
}
