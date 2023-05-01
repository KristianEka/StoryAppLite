package com.ekachandra.storyapplite.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun setLogout() {
        viewModelScope.launch {
            userRepository.setLogout()
        }
    }

    fun getAllStories(token: String): LiveData<PagingData<ListStoryItem>> =
        userRepository.getStory(token).cachedIn(viewModelScope)

    fun getToken(): LiveData<String> = userRepository.getToken()
    fun isLoading(): LiveData<Boolean> = userRepository.isLoading
    fun isError(): LiveData<Boolean> = userRepository.isError
    fun isUpdated(): LiveData<Boolean> = userRepository.isUpdate
}