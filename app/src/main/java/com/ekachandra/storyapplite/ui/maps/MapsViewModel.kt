package com.ekachandra.storyapplite.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.remote.response.ListStoryItem

class MapsViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getAllStoriesMap(token: String, enableLocation: Int): LiveData<List<ListStoryItem>> {
        return userRepository.getAllStoresMap(token, enableLocation)
    }

    fun getToken(): LiveData<String> = userRepository.getToken()
    fun isLoading(): LiveData<Boolean> = userRepository.isLoading
    fun isError(): LiveData<Boolean> = userRepository.isError
}