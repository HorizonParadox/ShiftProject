package com.example.projectforshift.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projectforshift.data.models.Card
import java.io.Serializable

@Entity(tableName = "history_table")
data class HistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardNumber: String
)