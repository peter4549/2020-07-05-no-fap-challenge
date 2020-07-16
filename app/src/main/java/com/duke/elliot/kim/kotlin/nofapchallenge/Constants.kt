package com.duke.elliot.kim.kotlin.nofapchallenge

const val DATA_BASE_NAME = "history_database_v1.1"

const val CHALLENGING = 0
const val FAILURE = 1
const val SUCCESS = 2

enum class ViewModelOperation {
    INSERT,
    UPDATE,
    DELETE
}

object Periods {
    const val STEP_00 = 0
    const val STEP_01 = 10
    const val STEP_02 = 40
    const val STEP_03 = 70
    const val STEP_04 = 100
    const val STEP_05 = 150
    const val STEP_06 = 200
    const val STEP_07 = 270
    const val STEP_08 = 365
}

object Titles {
    const val STEP_00 = "Slave"
    const val STEP_01 = "Commoner"
    const val STEP_02 = "Knight"
    const val STEP_03 = "Baronet"
    const val STEP_04 = "Baron"
    const val STEP_05 = "Viscount"
    const val STEP_06 = "Count"
    const val STEP_07 = "Marquess"
    const val STEP_08 = "Duke"
}