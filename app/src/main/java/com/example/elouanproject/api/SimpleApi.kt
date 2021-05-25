package com.example.elouanproject.api

import com.example.elouanproject.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface SimpleApi {
    @GET("posts")
    fun getPost(): Call<List<Post>>
}