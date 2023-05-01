package com.ekachandra.storyapplite.data.di

import android.content.Context
import com.ekachandra.storyapplite.data.UserRepository
import com.ekachandra.storyapplite.data.local.UserPreference
import com.ekachandra.storyapplite.data.local.dataStore
import com.ekachandra.storyapplite.data.local.room.StoryDatabase
import com.ekachandra.storyapplite.data.remote.retrofit.ApiConfig

object Injection {
    fun getRepository(context: Context): UserRepository {
        return UserRepository(
            ApiConfig.getApiService(),
            UserPreference.getInstance(dataStore = context.dataStore),
            StoryDatabase.getDatabase(context)
        )
    }
}