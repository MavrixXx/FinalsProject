package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView

class Bookmark_Activity : Activity() {

    private lateinit var bookmarkListView: ListView
    private lateinit var emptyMessageTextView: TextView
    private lateinit var bookmarkedPlants: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        bookmarkListView = findViewById(R.id.bookmarkList)
        emptyMessageTextView = findViewById(R.id.emptyMessageText)
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        val backButton = findViewById<ImageButton>(R.id.backImageButton)
        val homeButton = findViewById<ImageButton>(R.id.homeImageButton)
        val profileButton = findViewById<ImageButton>(R.id.userImageButton)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")

        val finalPassword = newPassword ?: password

        if (bookmarkedPlants.isEmpty()) {
            emptyMessageTextView.visibility = View.VISIBLE
            bookmarkListView.visibility = View.GONE
        } else {
            emptyMessageTextView.visibility = View.GONE
            bookmarkListView.visibility = View.VISIBLE
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bookmarkedPlants)
            bookmarkListView.adapter = adapter
        }

        backButton.setOnClickListener {
            val backIntent = Intent(this, Main_Dashboard_Activity::class.java)
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

    }
}