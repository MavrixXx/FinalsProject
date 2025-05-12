package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.Toast

class Premium_Activity : Activity() {

    private lateinit var bookmarkedPlants: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.premium)

        val backButton = findViewById<ImageButton>(R.id.backImageButton)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val finalPassword = intent.getStringExtra("PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        backButton.setOnClickListener {
            val backIntent = Intent(this, Settings_Activity::class.java)
            backIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            backIntent.putExtra("USERNAME", username)
            backIntent.putExtra("EMAIL", email)
            backIntent.putExtra("PHONE", phone)
            backIntent.putExtra("ADDRESS", address)
            backIntent.putExtra("PASSWORD", finalPassword)
            startActivity(backIntent)
        }

        val getPrem1 = findViewById<Button>(R.id.GetPrem1)
        val getPrem2 = findViewById<Button>(R.id.GetPrem2)
        val getPrem3 = findViewById<Button>(R.id.GetPrem3)

        getPrem1.setOnClickListener {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show()
        }

        getPrem2.setOnClickListener {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show()
        }

        getPrem3.setOnClickListener {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show()
        }
    }
}