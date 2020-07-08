package com.duke.elliot.kim.kotlin.nofapchallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(val targetPeriod: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var status: Int = 0
    var endDate: Int = 0
}