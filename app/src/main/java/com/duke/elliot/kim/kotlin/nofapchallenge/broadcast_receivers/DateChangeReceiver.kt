package com.duke.elliot.kim.kotlin.nofapchallenge.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.duke.elliot.kim.kotlin.nofapchallenge.MainActivity

class DateChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_DATE_CHANGED -> Toast.makeText(context, "Date changed!!!", Toast.LENGTH_LONG).show()
            else -> {  }
        }

    }

    private fun startUpdateService() {

    }
}