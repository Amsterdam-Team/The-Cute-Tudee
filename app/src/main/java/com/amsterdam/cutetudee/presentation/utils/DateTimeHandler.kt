package com.amsterdam.cutetudee.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date

class DateTimeHandler : IDateTimeHandler {
    override fun getCurrentDateInMillis(): Long {
        val currentLocalDateTime: LocalDateTime = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return currentLocalDateTime.second * 1000L
    }

    override fun getStringDateFromMillis(millis: Long, format: String): String {
        val formatter = SimpleDateFormat(format, androidx.compose.ui.text.intl.Locale.current.platformLocale)
        return formatter.format(Date(millis))
    }
}