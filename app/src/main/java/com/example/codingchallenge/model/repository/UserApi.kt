package com.example.codingchallenge.model.repository

import com.example.codingchallenge.model.data.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/")
    suspend fun getUsers(@Query("results") numberOfResults: Int): Response<UserResponse>

}