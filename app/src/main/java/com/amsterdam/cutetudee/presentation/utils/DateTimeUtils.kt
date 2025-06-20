package com.amsterdam.cutetudee.presentation.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.until
import java.time.format.TextStyle
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
