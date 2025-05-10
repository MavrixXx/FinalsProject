package com.example.florasense.data.api

import com.example.florasense.data.model.UpdateUserResponse
import com.example.florasense.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("users")
    suspend fun registerUser(@Body user: UserModel): Response<UserModel>

    @POST("users/login")
    suspend fun loginUser(@Body user: UserModel): Response<UserModel>

    @PUT("users/update")
    suspend fun updateUser(@Body user: UserModel): Response<UpdateUserResponse>
}


