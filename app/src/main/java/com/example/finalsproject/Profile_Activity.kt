package com.example.finalsproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class Profile_Activity : Activity() {

    private lateinit var bookmarkedPlants: ArrayList<String>
    private lateinit var updatedPassword: String
    private lateinit var updatedEmail: String
    private lateinit var updatedUsername: String
    private lateinit var updatedPhone: String
    private lateinit var updatedAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backButton = findViewById<ImageButton>(R.id.backImageButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsImageButton)
        val homeButton = findViewById<ImageButton>(R.id.homeImageButton)
        val bookmarkButton = findViewById<ImageButton>(R.id.bookmarkImageButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        val usernameTextView = findViewById<TextView>(R.id.username)
        val emailTextView = findViewById<TextView>(R.id.userEmail)
        val passwordTextView = findViewById<TextView>(R.id.passwordInfoText)
        val usernameInfoText = findViewById<TextView>(R.id.usernameInfoText)
        val emailInfoText = findViewById<TextView>(R.id.emailInfoText)
        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        val profilePicture = findViewById<ImageView>(R.id.profilePicture)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()
        updatedPassword = intent.getStringExtra("PASSWORD") ?: ""
        updatedEmail = intent.getStringExtra("EMAIL") ?: ""
        updatedPhone = intent.getStringExtra("PHONE") ?: ""
        updatedUsername = intent.getStringExtra("USERNAME") ?: ""
        updatedAddress = intent.getStringExtra("ADDRESS") ?: ""

        val finalPassword = newPassword ?: password

        usernameTextView.text = username ?: "Guest"
        emailTextView.text = email ?: "sample@email.com"
        usernameInfoText.text = username ?: "enter username"
        emailInfoText.text = email ?: "enter email"
        passwordTextView.text = generateAsterisksForPassword(finalPassword)

        val initialProfileImageUri = intent.getStringExtra("PROFILE_IMAGE_URI")
        if (initialProfileImageUri != null) {
            val uri = Uri.parse(initialProfileImageUri)
            profilePicture.setImageURI(uri)
        }

        backButton.setOnClickListener {
            val backIntent = Intent(this, Main_Dashboard_Activity::class.java)
            backIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            backIntent.putExtra("USERNAME", username)
            backIntent.putExtra("EMAIL", email)
            backIntent.putExtra("PASSWORD", finalPassword)
            startActivity(backIntent)
        }

        settingsButton.setOnClickListener {
            val settingsIntent = Intent(this, Settings_Activity::class.java)
            settingsIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            settingsIntent.putExtra("USERNAME", username)
            settingsIntent.putExtra("EMAIL", email)
            settingsIntent.putExtra("PASSWORD", finalPassword)
            startActivity(settingsIntent)
        }

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, Main_Dashboard_Activity::class.java)
            homeIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            homeIntent.putExtra("USERNAME", username)
            homeIntent.putExtra("EMAIL", email)
            homeIntent.putExtra("PASSWORD", finalPassword)
            startActivity(homeIntent)
        }

        bookmarkButton.setOnClickListener {
            val bookmarkIntent = Intent(this, Bookmark_Activity::class.java)
            bookmarkIntent.putStringArrayListExtra("bookmarked_plants", ArrayList(bookmarkedPlants))
            bookmarkIntent.putExtra("USERNAME", username)
            bookmarkIntent.putExtra("EMAIL", email)
            bookmarkIntent.putExtra("PASSWORD", finalPassword)
            startActivity(bookmarkIntent)
        }

        editProfileButton.setOnClickListener {
            val editProfileIntent = Intent(this, Edit_Profile_Activity::class.java)
            editProfileIntent.putExtra("USERNAME", username)
            editProfileIntent.putExtra("EMAIL", email)
            editProfileIntent.putExtra("PASSWORD", finalPassword)
            startActivityForResult(editProfileIntent, 1001)
        }

        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }

    private fun generateAsterisksForPassword(password: String?): String {
        return if (!password.isNullOrEmpty()) {
            "*".repeat(password.length)
        } else {
            "No password set"
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                val loginIntent = Intent(this, Login_Activity::class.java)
                startActivity(loginIntent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == RESULT_OK) {

            val usernameTextView = findViewById<TextView>(R.id.username)
            val emailTextView = findViewById<TextView>(R.id.userEmail)
            val passwordTextView = findViewById<TextView>(R.id.passwordInfoText)
            val usernameInfoText = findViewById<TextView>(R.id.usernameInfoText)
            val phoneInfoText = findViewById<TextView>(R.id.phoneNumberInfoText)
            val addressInfoText = findViewById<TextView>(R.id.addressInfoText)
            val emailInfoText = findViewById<TextView>(R.id.emailInfoText)
            val profilePicture = findViewById<ImageView>(R.id.profilePicture)

            val updatedUsername = data?.getStringExtra("UPDATED_USERNAME")
            val updatedEmail = data?.getStringExtra("UPDATED_EMAIL")
            val updatedPhone = data?.getStringExtra("UPDATED_PHONE")
            val updatedAddress = data?.getStringExtra("UPDATED_ADDRESS")
            val updatedPassword = data?.getStringExtra("UPDATED_PASSWORD")
            val updatedProfileImageUri = data?.getStringExtra("UPDATED_PROFILE_IMAGE")

            usernameTextView.text = updatedUsername
            emailTextView.text = updatedEmail
            usernameInfoText.text = updatedUsername
            emailInfoText.text = updatedEmail
            phoneInfoText.text = updatedPhone
            addressInfoText.text = updatedAddress
            passwordTextView.text = updatedPassword
            passwordTextView.text = generateAsterisksForPassword(updatedPassword)

            updatedProfileImageUri?.let {
                val uri = Uri.parse(it)
                profilePicture.setImageURI(uri)
            }
        }
    }
}
