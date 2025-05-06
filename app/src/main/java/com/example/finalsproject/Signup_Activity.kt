package com.example.finalsproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.florasense.data.model.UserModel
import com.example.florasense.viewModel.UserViewModel

class Signup_Activity : AppCompatActivity() {

    val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        /*val usernameEditText = findViewById<EditText>(R.id.usernameEdittext)
        val emailEditText = findViewById<EditText>(R.id.emailEdittext)
        val passwordEditText = findViewById<EditText>(R.id.passwordEdittext)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEdittext)
        val signupButton = findViewById<Button>(R.id.signupButton)
        val alreadyHaveAccount = findViewById<TextView>(R.id.alreadyHaveAnAccount)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()

                val loginIntent = Intent(this, Login_Activity::class.java)
                loginIntent.putExtra("USERNAME", username)
                loginIntent.putExtra("EMAIL", email)
                loginIntent.putExtra("PASSWORD", password)
                startActivity(loginIntent)
            }
        }

        alreadyHaveAccount.setOnClickListener {
            val username = intent.getStringExtra("USERNAME")
            val email = intent.getStringExtra("EMAIL")
            val password = intent.getStringExtra("PASSWORD")
            val loginIntent = Intent(this, Login_Activity::class.java)
            loginIntent.putExtra("USERNAME", username)
            loginIntent.putExtra("EMAIL", email)
            loginIntent.putExtra("PASSWORD", password)
            startActivity(loginIntent)
        }

         */

        addUser()
    }

    private fun addUser(){
        findViewById<Button>(R.id.signupButton).setOnClickListener {
            val usernameEditText = findViewById<EditText>(R.id.usernameEdittext).text.toString()
            val emailEditText = findViewById<EditText>(R.id.emailEdittext).text.toString()
            val passwordEditText = findViewById<EditText>(R.id.passwordEdittext).text.toString()
            val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEdittext).text.toString()

            val user = UserModel(
                username = usernameEditText,
                email = emailEditText,
                password = passwordEditText,
                confirmPassword = confirmPasswordEditText
            )

            userViewModel.registerUser(user)

        }



    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.matches(emailPattern.toRegex())
    }
}
