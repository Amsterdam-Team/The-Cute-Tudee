package com.amsterdam.cutetudee.presentation.component.tast_status

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

enum class TaskStatusUi(
    @StringRes val labelRes: Int,
    val containerColor: @Composable () -> Color,
    val contentColor: @Composable () -> Color
) {
    TODO(
        labelRes = R.string.todo,
        containerColor = { AppTheme.color.yellowVariant },
        contentColor = { AppTheme.color.yellowAccent }
    ),

    IN_PROGRESS(
        labelRes = R.string.in_progress,
        containerColor = { AppTheme.color.purpleVariant },
        contentColor = { AppTheme.color.purpleAccent }
    ),

    DONE(
        labelRes = R.string.done,
        containerColor = { AppTheme.color.greenVariant },
        contentColor = { AppTheme.color.greenAccent }
    )
}