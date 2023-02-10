package com.example.projectforshift.data.network

import javax.inject.Inject
class ApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllCardInfo() = apiService.getAllCardInfo()
}