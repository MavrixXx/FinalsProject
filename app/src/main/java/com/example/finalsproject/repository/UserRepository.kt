package com.example.florasense.data.repository

import com.example.florasense.data.api.RetroFitInstance
import com.example.florasense.data.model.UserModel

class UserRepository {
    suspend fun registerUser(user: UserModel) = RetroFitInstance.api.registerUser(user)
}