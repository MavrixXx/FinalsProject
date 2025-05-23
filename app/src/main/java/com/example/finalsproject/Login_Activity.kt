package com.example.finalsproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.finalsproject.data.api.Session
import com.example.florasense.data.model.UserModel
import com.example.florasense.viewModel.UserViewModel

class Login_Activity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

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
        val phone = intent.getStringExtra("PHONE")
        val address = intent.getStringExtra("ADDRESS")
        var password = intent.getStringExtra("PASSWORD")
        val newPassword = intent.getStringExtra("NEW_PASSWORD")
        if (newPassword != null) {
            password = newPassword
        }

        loginButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val user = UserModel(email = enteredEmail, password = enteredPassword, username = "", phone = "", address = "")

                userViewModel.loginUser(user)

                userViewModel.user.observe(this){userResponse ->
                    if (userResponse != null) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val mainDashboardIntent = Intent(this, Main_Dashboard_Activity::class.java)
                        mainDashboardIntent.putExtra("USERNAME", userResponse.username)
                        mainDashboardIntent.putExtra("EMAIL", userResponse.email)
                        mainDashboardIntent.putExtra("PASSWORD", enteredPassword)
                        mainDashboardIntent.putExtra("PHONE", userResponse.phone)
                        mainDashboardIntent.putExtra("ADDRESS", userResponse.address)
                        mainDashboardIntent.putExtra("USER_ID", userResponse._id)
                        Session.userID = userResponse._id.toString()
                        startActivity(mainDashboardIntent)
                    }
                }

                userViewModel.loginError.observe(this, Observer { errorMessage ->
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                })
            }
        }

        dontHaveAccount.setOnClickListener {
            val signupIntent = Intent(this, Signup_Activity::class.java)
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