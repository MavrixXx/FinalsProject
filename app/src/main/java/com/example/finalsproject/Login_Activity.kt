package com.example.finalsproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Login_Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEdittext)
        val passwordEditText = findViewById<EditText>(R.id.passwordEdittext)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val dontHaveAccount = findViewById<TextView>(R.id.dontHaveAnAccount)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        var password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        if (newPassword != null) {
            password = newPassword
        }

        loginButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()){
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (enteredEmail != email || enteredPassword != password){
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val mainDashboardIntent = Intent(this, Main_Dashboard_Activity::class.java)
                mainDashboardIntent.putExtra("USERNAME", username)
                mainDashboardIntent.putExtra("EMAIL", email)
                mainDashboardIntent.putExtra("PASSWORD", password)
                mainDashboardIntent.putExtra("NEW_PASSWORD", newPassword)
                startActivity(mainDashboardIntent)
            }
        }

        dontHaveAccount.setOnClickListener {
            val signupIntent = Intent(this, Signup_Activity::class.java)
            signupIntent.putExtra("USERNAME", username)
            signupIntent.putExtra("EMAIL", email)
            signupIntent.putExtra("PASSWORD", password)
            signupIntent.putExtra("NEW_PASSWORD", newPassword)
            startActivity(signupIntent)
        }

        forgotPasswordText.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun showForgotPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val emailEditText = dialogView.findViewById<EditText>(R.id.forgotPasswordEmailEditText)
        val email = intent.getStringExtra("EMAIL")

        builder.setView(dialogView)
            .setCancelable(true)
            .setPositiveButton("Submit") { _, _ ->
                val enteredEmail = emailEditText.text.toString()
                if (enteredEmail.isEmpty()) {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                } else {
                    if (enteredEmail == email) {
                        showResetPasswordDialog()
                    } else {
                        Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun showResetPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_reset_password, null)
        val newPasswordEditText = dialogView.findViewById<EditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = dialogView.findViewById<EditText>(R.id.confirmPasswordEditText)

        builder.setView(dialogView)
            .setCancelable(true)
            .setPositiveButton("Reset Password") { _, _ ->
                val newPassword = newPasswordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
                } else if (newPassword != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show()

                    val email = intent.getStringExtra("EMAIL")
                    val username = intent.getStringExtra("USERNAME")
                    val password = intent.getStringExtra("PASSWORD")

                    val loginIntent = Intent(this, Login_Activity::class.java)
                    loginIntent.putExtra("NEW_PASSWORD", newPassword)
                    loginIntent.putExtra("EMAIL", email)
                    loginIntent.putExtra("USERNAME", username)
                    startActivity(loginIntent)
                    finish()
                }
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}