package com.example.finalsproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalsproject.repository.BookmarkRepository
import com.example.florasense.data.model.BookmarkModel
import kotlinx.coroutines.launch

class BookmarkViewModel: ViewModel() {

    private val bookmarkRepository = BookmarkRepository()
    private val _bookmark = MutableLiveData<BookmarkModel?>()
    val bookmark: MutableLiveData<BookmarkModel?> = _bookmark
    private val _plantName = MutableLiveData<String>()
    val plantName: MutableLiveData<String> = _plantName
    private val _bookmarks = MutableLiveData<List<BookmarkModel>?>()
    val bookmarks: MutableLiveData<List<BookmarkModel>?> = _bookmarks

    fun addBookmark(bookmark: BookmarkModel){
        viewModelScope.launch {
            val response = bookmarkRepository.addBookmark(bookmark)
            if (response.isSuccessful) {
                _bookmark.value = response.body()
            }
        }
    }

    fun getBookmarks(plantName: String) {
        viewModelScope.launch {
            val response = bookmarkRepository.getBookmarks(plantName.toString())
            if (response.isSuccessful) {
                _bookmarks.value = response.body()
            } else {
                _bookmarks.value = null
            }
        }
    }


}