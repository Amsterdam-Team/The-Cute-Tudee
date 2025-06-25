package com.amsterdam.cutetudee.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

fun LocalDate.monthDays(): List<Int> {
    val start = this
    val end = start.plus(1, DateTimeUnit.MONTH)
    val daysInMonth = start.until(end, DateTimeUnit.DAY)
    return (1..daysInMonth).toList()
}

fun LocalDate.getCurrentMonthDays(day: Int): String {
    val now = this
    val date = LocalDate(now.year, now.month, day)
    val dayOfWeek = DayOfWeek.valueOf(date.dayOfWeek.name)
    return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}

fun getCurrentDateInMillis(): Long =
    getCurrentLocalDate().getDateInMillisFromLocalDate()

fun getCurrentLocalDate(): LocalDate =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Long.getStringDateFromMillis(
    format: String = "EEE, MMM dd",
): String = SimpleDateFormat(format, androidx.compose.ui.text.intl.Locale.current.platformLocale)
    .format(Date(this))

fun getCurrentStringDate(format: String = "EEE, MMM dd"): String =
    getCurrentDateInMillis().getStringDateFromMillis(format)

fun LocalDate.getStringDateFromLocalDate(format: String = "EEE, MMM dd"): String =
    this.getDateInMillisFromLocalDate().getStringDateFromMillis(format)


fun LocalDate.getDateInMillisFromLocalDate(): Long =
    this.toEpochDays() * 24 * 60 * 60 * 1000L

fun Long.getLocalDateFromMillis(): LocalDate =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
