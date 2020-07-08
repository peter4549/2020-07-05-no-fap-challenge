package com.duke.elliot.kim.kotlin.nofapchallenge.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.duke.elliot.kim.kotlin.nofapchallenge.DATA_BASE_NAME
import com.duke.elliot.kim.kotlin.nofapchallenge.MainActivity
import com.duke.elliot.kim.kotlin.nofapchallenge.ViewModelOperation
import kotlinx.coroutines.*

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    lateinit var targetHistory: History

    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        DATA_BASE_NAME
    ).build()

    fun getAll(): LiveData<MutableList<History>> = database.dao().getAll()

    fun insert(history: History) {
        MainActivity.viewModelOperation = ViewModelOperation.INSERT
        scope.launch {
            database.dao().insert(history)
        }
    }

    fun update(history: History) {
        MainActivity.viewModelOperation = ViewModelOperation.UPDATE
        scope.launch {
            database.dao().update(history)
            targetHistory = history
        }
    }

    fun delete(history: History) {
        MainActivity.viewModelOperation = ViewModelOperation.DELETE
        scope.launch {
            database.dao().delete(history)
            targetHistory = history
        }
    }
}