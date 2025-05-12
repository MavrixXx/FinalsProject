package com.example.finalsproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalsproject.repository.BookmarkRepository
import com.example.florasense.data.model.BookmarkModel
import com.example.florasense.data.model.UserModel
import kotlinx.coroutines.launch

class BookmarkViewModel: ViewModel() {

    private val bookmarkRepository = BookmarkRepository()
    private val _bookmark = MutableLiveData<BookmarkModel?>()
    val bookmark: MutableLiveData<BookmarkModel?> = _bookmark

    fun addBookmark(bookmark: BookmarkModel){
        viewModelScope.launch {
            val response = bookmarkRepository.addBookmark(bookmark)
            if (response.isSuccessful) {
                _bookmark.value = response.body()
            }
        }
    }

}