package com.example.florasense.data.model

data class UserModel(
    val _id: String? = null,
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String? = null

)
