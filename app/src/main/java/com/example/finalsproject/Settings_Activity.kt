package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

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
        val themeButton = findViewById<ImageView>(R.id.nextIcon)
        val languageButton = findViewById<ImageView>(R.id.nextIcon1)
        val premiumButton = findViewById<ImageView>(R.id.nextIcon5)
        val termsButton = findViewById<ImageView>(R.id.nextIcon7)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val finalPassword = intent.getStringExtra("PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        backButton.setOnClickListener {
            val backIntent = Intent(this, Profile_Activity::class.java)
            backIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            backIntent.putExtra("USERNAME", username)
            backIntent.putExtra("EMAIL", email)
            backIntent.putExtra("PHONE", phone)
            backIntent.putExtra("ADDRESS", address)
            backIntent.putExtra("PASSWORD", finalPassword)
            startActivity(backIntent)
        }

        languageButton.setOnClickListener {
            val languageIntent = Intent(this, Language_Activity::class.java)
            languageIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            languageIntent.putExtra("USERNAME", username)
            languageIntent.putExtra("EMAIL", email)
            languageIntent.putExtra("PHONE", phone)
            languageIntent.putExtra("ADDRESS", address)
            languageIntent.putExtra("PASSWORD", finalPassword)
            startActivity(languageIntent)
        }

        themeButton.setOnClickListener {
            val themeIntent = Intent(this, Theme_Activity::class.java)
            themeIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            themeIntent.putExtra("USERNAME", username)
            themeIntent.putExtra("EMAIL", email)
            themeIntent.putExtra("PHONE", phone)
            themeIntent.putExtra("ADDRESS", address)
            themeIntent.putExtra("PASSWORD", finalPassword)
            startActivity(themeIntent)
        }

        premiumButton.setOnClickListener {
            val premiumIntent = Intent(this, Premium_Activity::class.java)
            premiumIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            premiumIntent.putExtra("USERNAME", username)
            premiumIntent.putExtra("EMAIL", email)
            premiumIntent.putExtra("PHONE", phone)
            premiumIntent.putExtra("ADDRESS", address)
            premiumIntent.putExtra("PASSWORD", finalPassword)
            startActivity(premiumIntent)
        }
        termsButton.setOnClickListener {
            showTermsAndConditions()
        }

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, Main_Dashboard_Activity::class.java)
            homeIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            homeIntent.putExtra("USERNAME", username)
            homeIntent.putExtra("EMAIL", email)
            homeIntent.putExtra("PHONE", phone)
            homeIntent.putExtra("ADDRESS", address)
            homeIntent.putExtra("PASSWORD", finalPassword)
            startActivity(homeIntent)
        }

        profileButton.setOnClickListener {
            val profileIntent = Intent(this, Profile_Activity::class.java)
            profileIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            profileIntent.putExtra("USERNAME", username)
            profileIntent.putExtra("EMAIL", email)
            profileIntent.putExtra("PHONE", phone)
            profileIntent.putExtra("ADDRESS", address)
            profileIntent.putExtra("PASSWORD", finalPassword)
            startActivity(profileIntent)
        }

        bookmarkButton.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PHONE", phone)
            bookmarkIntent.putExtra("ADDRESS", address)
            bookmarkIntent.putExtra("PASSWORD", finalPassword)
            startActivity(bookmarkIntent)
        }

        developers.setOnClickListener {
            val developersIntent = Intent(this, Developers_Activity::class.java)
            developersIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            developersIntent.putExtra("USERNAME", username)
            developersIntent.putExtra("EMAIL", email)
            developersIntent.putExtra("PHONE", phone)
            developersIntent.putExtra("ADDRESS", address)
            developersIntent.putExtra("PASSWORD", finalPassword)
            startActivity(developersIntent)
        }
    }

    private fun showTermsAndConditions() {
        val dialogView = layoutInflater.inflate(R.layout.term_and_condition, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.show()
    }
}