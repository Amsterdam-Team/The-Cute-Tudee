package com.amsterdam.cutetudee.presentation.component.chip.tast_status

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.ColorType

enum class TaskStatusUi(
    @StringRes val labelRes: Int,
    private val containerColorProvider: ColorType,
    private val contentColorProvider: ColorType,
) {
    TODO(
        labelRes = R.string.todo,
        containerColorProvider = { AppTheme.color.yellowVariant },
        contentColorProvider = { AppTheme.color.yellowAccent },
    ),

    IN_PROGRESS(
        labelRes = R.string.in_progress,
        containerColorProvider = { AppTheme.color.purpleVariant },
        contentColorProvider = { AppTheme.color.purpleAccent },
    ),

    DONE(
        labelRes = R.string.done,
        containerColorProvider = { AppTheme.color.greenVariant },
        contentColorProvider = { AppTheme.color.greenAccent },
    ),
    ;

    val containerColor: Color
        @Composable get() = containerColorProvider()

    val contentColor: Color
        @Composable get() = contentColorProvider()
}

fun Task.Status.toTaskStatusUi(): TaskStatusUi =
    when (this) {
        Task.Status.TODO -> TaskStatusUi.TODO
        Task.Status.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
        Task.Status.DONE -> TaskStatusUi.DONE
    }
