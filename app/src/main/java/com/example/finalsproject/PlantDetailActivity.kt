package com.example.finalsproject

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class PlantDetailActivity : Activity() {

    private lateinit var plantNameTextView: TextView
    private lateinit var plantInfoTextView: TextView
    // You can add other views for plant details (like description, care instructions, etc.)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        plantNameTextView = findViewById(R.id.plantNameTextView)
        plantInfoTextView = findViewById(R.id.plantInfoTextView)
        // You can add other views to display more plant details

        val plantName = intent.getStringExtra("PLANT_NAME")
        val plantInfo = intent.getStringExtra("PLANT_INFO")
        plantNameTextView.text = plantName
        plantInfoTextView.text = plantInfo
        // You can set other plant details here
    }
}
