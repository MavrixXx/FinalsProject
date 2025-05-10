package com.example.florasense.data.model

data class UserModel(
    val _id: String? = null,
    val username: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
)

data class UpdateUserResponse(
    val success: Boolean,
    val message: String,
    val user: UserModel
)




