package com.amsterdam.cutetudee.presentation.utils

import androidx.compose.ui.text.intl.Locale
import java.time.YearMonth
import java.time.format.TextStyle

fun getNumberOfDays(): List<Int> {
    val currentMonth = YearMonth.now()

    return (1..currentMonth.lengthOfMonth()).map { day ->
        day
    }

}

fun getCurrentMonthDays(day: Int):  String {
   return YearMonth.now().atDay(day).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.current.platformLocale)
}
