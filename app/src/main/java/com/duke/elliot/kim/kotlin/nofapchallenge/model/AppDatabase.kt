package com.duke.elliot.kim.kotlin.nofapchallenge.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): HistoryDao
}