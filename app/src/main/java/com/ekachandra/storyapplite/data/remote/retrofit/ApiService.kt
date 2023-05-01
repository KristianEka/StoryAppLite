package com.ekachandra.storyapplite.data.remote.retrofit

import com.ekachandra.storyapplite.data.remote.response.DefaultResponse
import com.ekachandra.storyapplite.data.remote.response.DetailStoryResponse
import com.ekachandra.storyapplite.data.remote.response.GetAllStoriesResponse
import com.ekachandra.storyapplite.data.remote.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun addNewStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?,
    ): Call<DefaultResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetAllStoriesResponse

    @GET("stories")
    fun getAllStoriesMap(
        @Header("Authorization") token: String,
        @Query("location") location: Int,
    ): Call<GetAllStoriesResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<DetailStoryResponse>

}