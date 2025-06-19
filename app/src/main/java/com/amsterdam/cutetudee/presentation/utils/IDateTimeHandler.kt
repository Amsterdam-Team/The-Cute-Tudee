package com.amsterdam.cutetudee.presentation.utils

interface IDateTimeHandler {
    fun getCurrentDateInMillis(): Long
    fun getStringDateFromMillis(millis: Long, format: String): String
}