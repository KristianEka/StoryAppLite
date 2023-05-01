package com.ekachandra.storyapplite.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.di.Injection
import com.ekachandra.storyapplite.ui.auth.AuthViewModel
import com.ekachandra.storyapplite.ui.camera.CameraViewModel
import com.ekachandra.storyapplite.ui.detail.DetailViewModel
import com.ekachandra.storyapplite.ui.main.MainViewModel
import com.ekachandra.storyapplite.ui.maps.MapsViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context,
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.getRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}