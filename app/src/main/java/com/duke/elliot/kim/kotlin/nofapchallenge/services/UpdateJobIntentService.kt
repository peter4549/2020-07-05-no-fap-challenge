package com.duke.elliot.kim.kotlin.nofapchallenge.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.duke.elliot.kim.kotlin.nofapchallenge.KEY_CHALLENGING
import com.duke.elliot.kim.kotlin.nofapchallenge.KEY_DATE_COUNT
import com.duke.elliot.kim.kotlin.nofapchallenge.MainActivity
import com.duke.elliot.kim.kotlin.nofapchallenge.PROGRESS_PREFERENCES

class UpdateJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val preferences = getSharedPreferences(PROGRESS_PREFERENCES, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        if (preferences.getBoolean(KEY_CHALLENGING, false)) {
            editor.putInt(KEY_DATE_COUNT, preferences.getInt(KEY_DATE_COUNT, 0) + 1)
            editor.apply()
        }

        if (MainActivity.isAppRunning)
            notifyDateUpdate()
    }

    private fun notifyDateUpdate() {
        val intent = Intent(MainActivity.ACTION_DATE_UPDATED)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}