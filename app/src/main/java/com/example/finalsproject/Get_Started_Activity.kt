package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Get_Started_Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<Button>(R.id.signupButton)

        loginButton.setOnClickListener {
            val loginIntent = Intent(this, Login_Activity::class.java)
            startActivity(loginIntent)
        }

        signupButton.setOnClickListener {
            val signupIntent = Intent(this, Signup_Activity::class.java)
            startActivity(signupIntent)
        }

    }
}