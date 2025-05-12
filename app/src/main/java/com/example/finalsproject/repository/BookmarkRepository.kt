package com.example.finalsproject.repository

import com.example.florasense.data.api.RetroFitInstance
import com.example.florasense.data.model.BookmarkModel
import retrofit2.Response

class BookmarkRepository {
    suspend fun addBookmark(bookmark: BookmarkModel) = RetroFitInstance.api.addBookmark(bookmark)

    suspend fun getBookmarks(): Response<List<BookmarkModel>> {
        return RetroFitInstance.api.getBookmarks()
    }



}