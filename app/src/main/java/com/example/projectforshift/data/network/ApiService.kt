package com.example.projectforshift.data.network

import com.example.projectforshift.data.models.Card
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/45717362")
    suspend fun getAllCardInfo(): Response<List<Card>>
}