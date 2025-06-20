package com.amsterdam.cutetudee.presentation.utils

import kotlinx.datetime.LocalDate

interface IDateTimeHandler {
    fun getCurrentDateInMillis(): Long

    fun getStringDateFromMillis(
        millis: Long,
        format: String,
    ): String

    fun getCurrentStringDate(format: String): String

    fun getDateFromMillis(millis: Long): LocalDate
}
