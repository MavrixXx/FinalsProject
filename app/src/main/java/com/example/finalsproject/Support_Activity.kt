package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Support_Activity : Activity() {

    private lateinit var bookmarkedPlants: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val supportButton = findViewById<Button>(R.id.supportButton)

        // Get data from intent
        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val finalPassword = intent.getStringExtra("PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        supportButton.setOnClickListener {
            // Add your support contact functionality here
            // For example, opening an email client:
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("florasense@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            try {
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            } catch (ex: android.content.ActivityNotFoundException) {
                // Handle case where no email client is available
            }
        }
    }
}