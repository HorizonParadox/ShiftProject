package com.example.projectforshift.data.network

import com.example.projectforshift.data.models.Card
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{bin}")
    suspend fun getAllCardInfo(@Path("bin") bin: String): Response<Card>
}