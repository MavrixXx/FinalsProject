package com.example.finalsproject.repository

import com.example.florasense.data.api.RetroFitInstance
import com.example.florasense.data.model.BookmarkModel

class BookmarkRepository {
    suspend fun addBookmark(bookmark: BookmarkModel) = RetroFitInstance.api.addBookmark(bookmark)
    suspend fun getBookmarks(plantName: String) = RetroFitInstance.api.getBookmarks(plantName)


}