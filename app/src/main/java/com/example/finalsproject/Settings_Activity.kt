package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton

class Settings_Activity : Activity() {

    private lateinit var bookmarkedPlants: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.backImageButton)
        val developers = findViewById<ImageButton>(R.id.nextIcon8)
        val homeButton = findViewById<ImageButton>(R.id.homeImageButton)
        val bookmarkButton = findViewById<ImageButton>(R.id.bookmarkImageButton)
        val profileButton = findViewById<ImageButton>(R.id.userImageButton)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val finalPassword = intent.getStringExtra("PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        backButton.setOnClickListener {
            val backIntent = Intent(this, Profile_Activity::class.java)
            backIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            backIntent.putExtra("USERNAME", username)
            backIntent.putExtra("EMAIL", email)
            backIntent.putExtra("PASSWORD", finalPassword)
            startActivity(backIntent)
        }

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, Main_Dashboard_Activity::class.java)
            homeIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            homeIntent.putExtra("USERNAME", username)
            homeIntent.putExtra("EMAIL", email)
            homeIntent.putExtra("PASSWORD", finalPassword)
            startActivity(homeIntent)
        }

        profileButton.setOnClickListener {
            val profileIntent = Intent(this, Profile_Activity::class.java)
            profileIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            profileIntent.putExtra("USERNAME", username)
            profileIntent.putExtra("EMAIL", email)
            profileIntent.putExtra("PASSWORD", finalPassword)
            startActivity(profileIntent)
        }

        bookmarkButton.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PASSWORD", finalPassword)
            startActivity(bookmarkIntent)
        }

        developers.setOnClickListener {
            val developersIntent = Intent(this, Developers_Activity::class.java)
            developersIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            developersIntent.putExtra("USERNAME", username)
            developersIntent.putExtra("EMAIL", email)
            developersIntent.putExtra("PASSWORD", finalPassword)
            startActivity(developersIntent)
        }
    }
}