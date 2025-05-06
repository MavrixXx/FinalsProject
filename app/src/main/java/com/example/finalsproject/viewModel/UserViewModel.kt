package com.example.florasense.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.florasense.data.model.UserModel
import com.example.florasense.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val userRepository = UserRepository()
    private val _user = MutableLiveData<UserModel>()
    val user: MutableLiveData<UserModel> = _user

    fun registerUser(user: UserModel) {
        viewModelScope.launch {
            val response = userRepository.registerUser(user)
            if (response.isSuccessful) {
                _user.value = response.body()
            }
        }
    }
}