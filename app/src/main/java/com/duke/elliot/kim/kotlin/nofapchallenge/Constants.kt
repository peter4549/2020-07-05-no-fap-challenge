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