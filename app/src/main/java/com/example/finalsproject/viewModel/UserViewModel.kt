package com.example.florasense.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.florasense.data.model.UserModel
import com.example.florasense.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _user = MutableLiveData<UserModel?>()
    val user: MutableLiveData<UserModel?> = _user
    private val _loginError = MutableLiveData<String>()
    val loginError: MutableLiveData<String> = _loginError
    private val _updateError = MutableLiveData<String>()
    val updateError: MutableLiveData<String> = _updateError

    // Register user
    fun registerUser(user: UserModel) {
        viewModelScope.launch {
            val response = userRepository.registerUser(user)
            if (response.isSuccessful) {
                _user.value = response.body()
            }
        }
    }

    // Login user
    fun loginUser(user: UserModel) {
        viewModelScope.launch {
            val response = userRepository.loginUser(user)
            if (response.isSuccessful) {
                _user.value = response.body()
            } else {
                _loginError.value = "Login failed: ${response.message()}"
            }
        }
    }


    fun updateUser(user: UserModel) {
        viewModelScope.launch {
            val response = userRepository.updateUser(user)
            if (response.isSuccessful) {
                val updatedUser = response.body()?.user
                if (updatedUser != null) {
                    _user.value = updatedUser
                } else {
                    _updateError.value = "Failed to parse updated user."
                }
            } else {
                _updateError.value = "Profile update failed: ${response.message()}"
            }
        }
    }

}
