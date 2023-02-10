package com.example.projectforshift.db.repository

import androidx.lifecycle.LiveData
import com.example.projectforshift.db.dao.HistoryDao
import com.example.projectforshift.db.model.HistoryModel

class HistoryRealization(private val historyDao: HistoryDao) : HistoryRepository {
    override val historyList: LiveData<List<HistoryModel>>
        get() = historyDao.getAllHistory()

    override suspend fun insert(cardItem: HistoryModel) {
        historyDao.insertCardInHistory(cardItem)
    }
}