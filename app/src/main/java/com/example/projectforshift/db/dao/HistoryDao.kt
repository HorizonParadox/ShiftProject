package com.example.projectforshift.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projectforshift.db.model.HistoryModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table")
    fun getAllHistory(): LiveData<List<HistoryModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCardInHistory(historyItem: HistoryModel)

    @Query("DELETE FROM history_table")
    suspend fun deleteAllHistory()
}
