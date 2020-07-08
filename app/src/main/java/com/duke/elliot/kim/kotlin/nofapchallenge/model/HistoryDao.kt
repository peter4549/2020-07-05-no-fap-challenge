package com.duke.elliot.kim.kotlin.nofapchallenge.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): LiveData<MutableList<History>>

    @Insert
    fun insert(history: History)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)
}