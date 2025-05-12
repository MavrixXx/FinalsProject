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
    private val _bookmarks = MutableLiveData<List<BookmarkModel>>()
    val bookmarks: MutableLiveData<List<BookmarkModel>> = _bookmarks
    

        fun addBookmark(bookmark: BookmarkModel){
            viewModelScope.launch {
                val response = bookmarkRepository.addBookmark(bookmark)
                if (response.isSuccessful) {
                    _bookmark.value = response.body()
                }
            }
        }

    fun getBookmarks() {
        viewModelScope.launch {
            try {
                val response = bookmarkRepository.getBookmarks()
                if (response.isSuccessful) {
                    _bookmarks.value = response.body()
                } else {
                    _bookmarks.value = emptyList()
                }
            } catch (e: Exception) {
                _bookmarks.value = emptyList()
            }
        }
    }

}
