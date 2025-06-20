package com.amsterdam.cutetudee.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date

class DateTimeHandler : IDateTimeHandler {
    override fun getCurrentDateInMillis(): Long {
        val nowInstant = Clock.System.now()
        val currentLocalDateTime = nowInstant.toLocalDateTime(TimeZone.currentSystemDefault())
        val currentDate: LocalDate = currentLocalDateTime.date
        return currentDate.toEpochDays() * 24 * 60 * 60 * 1000L
    }

    override fun getStringDateFromMillis(
        millis: Long,
        format: String,
    ): String {
        val formatter =
            SimpleDateFormat(format, androidx.compose.ui.text.intl.Locale.current.platformLocale)
        return formatter.format(Date(millis))
    }

    override fun getCurrentStringDate(format: String): String {
        val currentMillis = getCurrentDateInMillis()
        return getStringDateFromMillis(currentMillis, format)
    }

    override fun getDateFromMillis(millis: Long): LocalDate =
        Instant
            .fromEpochMilliseconds(millis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
}
