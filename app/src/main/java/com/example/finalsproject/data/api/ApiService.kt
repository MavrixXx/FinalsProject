package com.example.florasense.data.api

import com.example.florasense.data.model.BookmarkModel
import com.example.florasense.data.model.UpdateUserResponse
import com.example.florasense.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users")
    suspend fun registerUser(@Body user: UserModel): Response<UserModel>

    @POST("users/login")
    suspend fun loginUser(@Body user: UserModel): Response<UserModel>

    @PUT("users/update")
    suspend fun updateUser(@Body user: UserModel): Response<UpdateUserResponse>

    @GET("users/fetch/{userId}")
    suspend fun fetchUser(@Path("userId") userId: String): Response<UserModel>

    @POST("bookmarks")
    suspend fun addBookmark(@Body bookmark: BookmarkModel): Response<BookmarkModel>

    @GET("bookmarks")
    suspend fun getBookmarks(@Query("plantName") plantName: String): Response<List<BookmarkModel>>
}


