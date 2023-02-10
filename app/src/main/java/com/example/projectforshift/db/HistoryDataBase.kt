package com.example.projectforshift.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectforshift.db.dao.HistoryDao
import com.example.projectforshift.db.model.HistoryModel


@Database(entities = [HistoryModel::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun getDao(): HistoryDao

    companion object {
        private var database: RoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HistoryDataBase {
            return if (database == null) {
                database =
                    Room.databaseBuilder(context, HistoryDataBase::class.java, "history_database")
                        .build()
                database as HistoryDataBase
            } else {
                database as HistoryDataBase
            }
        }
    }
}
