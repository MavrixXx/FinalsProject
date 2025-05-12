package com.example.florasense.data.repository

import com.example.florasense.data.api.RetroFitInstance
import com.example.florasense.data.model.BookmarkModel
import com.example.florasense.data.model.UserModel

class UserRepository {
    suspend fun registerUser(user: UserModel) = RetroFitInstance.api.registerUser(user)
    suspend fun loginUser(user: UserModel) = RetroFitInstance.api.loginUser(user)
    suspend fun updateUser(user: UserModel) = RetroFitInstance.api.updateUser(user)
    suspend fun fetchUser(userId: String) = RetroFitInstance.api.fetchUser(userId)
}