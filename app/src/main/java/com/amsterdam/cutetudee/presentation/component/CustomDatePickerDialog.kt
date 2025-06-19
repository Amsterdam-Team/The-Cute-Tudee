package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    dateTimeHandler: IDateTimeHandler,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedDateMillis: Long? = null,
    selectableDates: SelectableDates? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    dismissButtonText: String = stringResource(R.string.cancel),
    titleText: String = stringResource(R.string.select_date),
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis
            ?: dateTimeHandler.getCurrentDateInMillis(),
        selectableDates = selectableDates ?: object : SelectableDates {}
    )

    val currentHeadlineText = remember(datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis != null) {
            dateTimeHandler.getStringDateFromMillis(
                datePickerState.selectedDateMillis!!,
                "EEE, MMM dd"
            )
        } else {
            dateTimeHandler.getCurrentStringDate(
                "EEE, MMM dd"
            )
        }
    }

    CustomDatePickerDialogContent(
        datePickerState = datePickerState,
        titleText = titleText,
        currentHeadlineText = currentHeadlineText,
        onDismissRequest = onDismissRequest,
        onDateSelected = onDateSelected,
        modifier = modifier,
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomDatePickerDialogContent(
    datePickerState: DatePickerState,
    titleText: String,
    currentHeadlineText: String,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonText: String = stringResource(R.string.ok),
    dismissButtonText: String = stringResource(R.string.cancel),
) {
    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.color.surface
        ),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                    onDismissRequest()
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(
                    confirmButtonText,
                    style = AppTheme.textStyle.label.large,
                    color = AppTheme.color.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    dismissButtonText,
                    style = AppTheme.textStyle.label.large,
                    color = AppTheme.color.primary,
                )
            }
        },
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        DatePickerDialogContent(
            datePickerState,
            titleText,
            currentHeadlineText
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogContent(
    datePickerState: DatePickerState,
    titleText: String,
    currentHeadlineText: String
) {
    DatePicker(
        state = datePickerState,
        colors = datePickerColors(),
        title = {
            Text(
                titleText,
                style = AppTheme.textStyle.label.large,
                color = AppTheme.color.title,
                modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
            )
        },
        headline = {
            Text(
                currentHeadlineText,
                modifier = Modifier.padding(start = 24.dp, end = 12.dp),
                style = AppTheme.textStyle.headLine.large,
                color = AppTheme.color.title,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun datePickerColors() = DatePickerDefaults.colors(
    selectedDayContentColor = AppTheme.color.onPrimary,
    selectedDayContainerColor = AppTheme.color.primary,
    disabledSelectedDayContentColor = AppTheme.color.onPrimary,
    disabledDayContentColor = AppTheme.color.title,
    todayContentColor = AppTheme.color.primary,
    todayDateBorderColor = AppTheme.color.primary,
    containerColor = AppTheme.color.surface,
    weekdayContentColor = AppTheme.color.title,
    dayContentColor = AppTheme.color.title,
    subheadContentColor = AppTheme.color.title,
    navigationContentColor = AppTheme.color.title,
    headlineContentColor = AppTheme.color.title,
    titleContentColor = AppTheme.color.title,
    dayInSelectionRangeContentColor = AppTheme.color.title,
    currentYearContentColor = AppTheme.color.title,
    yearContentColor = AppTheme.color.title,
    selectedYearContentColor = AppTheme.color.title,
    disabledYearContentColor = AppTheme.color.title,
    disabledSelectedDayContainerColor = AppTheme.color.title,
    selectedYearContainerColor = AppTheme.color.title,
    dateTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = AppTheme.color.surface,
        unfocusedContainerColor = AppTheme.color.surface,
        focusedIndicatorColor = AppTheme.color.title,
        unfocusedIndicatorColor = AppTheme.color.hint,
        focusedLabelColor = AppTheme.color.title,
        unfocusedLabelColor = AppTheme.color.title,
        focusedTextColor = AppTheme.color.title,
        unfocusedTextColor = AppTheme.color.hint,
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@ThemeAndLocalePreviews
@Composable
private fun CustomDatePickerDialogPreview() {
    CuteTudeeTheme {
        var showDatePicker by remember { mutableStateOf(false) }
        var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = 1718744400000,
            selectableDates = object : SelectableDates {}
        )
        selectedDateMillis?.let { millis ->
            val dateString = "19 June 25"
            Text("Selected: $dateString", style = MaterialTheme.typography.bodyLarge)
        }

        CustomDatePickerDialogContent(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { millis ->
                selectedDateMillis = millis
                showDatePicker = false
            },
            datePickerState = datePickerState,
            currentHeadlineText = "19 June 25",
            modifier = Modifier,
            confirmButtonText = stringResource(R.string.ok),
            dismissButtonText = stringResource(R.string.cancel),
            titleText = stringResource(R.string.select_date),
        )
    }
}