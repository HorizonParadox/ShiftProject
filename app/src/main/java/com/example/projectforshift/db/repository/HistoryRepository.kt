package com.example.projectforshift.db.repository

import androidx.lifecycle.LiveData
import com.example.projectforshift.db.model.HistoryModel

interface HistoryRepository {
    val historyList: LiveData<List<HistoryModel>>
    suspend fun insert(cardItem: HistoryModel)
}