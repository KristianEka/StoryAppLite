package com.ekachandra.storyapplite.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.remote.response.DefaultResponse
import com.ekachandra.storyapplite.data.remote.response.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) {
        userRepository.registerUser(name, email, password)
    }

    fun login(email: String, password: String) {
        userRepository.loginUser(email, password)
    }

    fun saveUser(token: String, isLogin: Boolean) {
        viewModelScope.launch {
            userRepository.saveUser(token, isLogin)
        }
    }

    fun getSession(): LiveData<Boolean> = userRepository.getSession()
    fun loginResponse(): LiveData<LoginResponse> = userRepository.loginResponse
    fun message(): LiveData<DefaultResponse> = userRepository.message
    fun isLoading(): LiveData<Boolean> = userRepository.isLoading
    fun isError(): LiveData<Boolean> = userRepository.isError
    fun isSuccessRegister(): LiveData<Boolean> = userRepository.isSuccessRegister
    fun isSuccessLogin(): LiveData<Boolean> = userRepository.isSuccessLogin
}