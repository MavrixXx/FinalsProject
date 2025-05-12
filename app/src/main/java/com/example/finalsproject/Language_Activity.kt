package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast

class Language_Activity : Activity() {

    private lateinit var bookmarkedPlants: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.language)

        val backButton = findViewById<ImageButton>(R.id.backImageButton)
        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)

        // Get data from intent
        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        val finalPassword = intent.getStringExtra("PASSWORD")
        bookmarkedPlants = intent.getStringArrayListExtra("bookmarked_plants") ?: ArrayList()

        // Set up the language dropdown
        val languages = arrayOf(
            "English (EN)",
            "Japanese (JA)",
            "Spanish (ES)",
            "French (FR)",
            "German (DE)",
            "Chinese (ZH)",
            "Korean (KO)",
            "Arabic (AR)",
            "Russian (RU)",
            "Portuguese (PT)"
        )

        val adapter = ArrayAdapter(this, R.layout.spinner_selected_item, languages)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        languageSpinner.adapter = adapter

        // Handle spinner item selection
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    applicationContext,
                    "Selected $selectedLanguage",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

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
    }
}