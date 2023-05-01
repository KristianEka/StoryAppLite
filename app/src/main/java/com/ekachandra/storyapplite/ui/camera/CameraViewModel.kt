package com.ekachandra.storyapplite.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.remote.response.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CameraViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun postStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Float?,
        lon: Float?,
    ) {
        userRepository.postStory(token, file, description, lat, lon)
    }

    fun getToken(): LiveData<String> = userRepository.getToken()
    fun message(): LiveData<DefaultResponse> = userRepository.message
    fun isLoading(): LiveData<Boolean> = userRepository.isLoading
    fun isError(): LiveData<Boolean> = userRepository.isError
}