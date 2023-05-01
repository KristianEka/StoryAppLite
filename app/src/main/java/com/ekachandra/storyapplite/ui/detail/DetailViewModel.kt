package com.ekachandra.storyapplite.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.remote.response.Story

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailStory(token: String, id: String): LiveData<Story> {
        return userRepository.getDetailStory(token, id)
    }

    fun getToken(): LiveData<String> = userRepository.getToken()
    fun isLoading(): LiveData<Boolean> = userRepository.isLoading
    fun isError(): LiveData<Boolean> = userRepository.isError
}