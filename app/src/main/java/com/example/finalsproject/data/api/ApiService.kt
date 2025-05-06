package com.example.florasense.data.api

import com.example.florasense.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    suspend fun registerUser(@Body user: UserModel): Response<UserModel>
}