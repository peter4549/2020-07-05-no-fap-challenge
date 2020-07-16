package com.duke.elliot.kim.kotlin.nofapchallenge.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.duke.elliot.kim.kotlin.nofapchallenge.services.UpdateJobIntentService

class DateChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_DATE_CHANGED -> startUpdateJobIntentService(context)
            else -> {  }
        }
    }

    private fun startUpdateJobIntentService(context: Context?) {
        if (context != null) {
            val intent = Intent(context, UpdateJobIntentService::class.java)
            context.startService(intent)
            UpdateJobIntentService().enqueueWork(context, intent)
        }
    }
}