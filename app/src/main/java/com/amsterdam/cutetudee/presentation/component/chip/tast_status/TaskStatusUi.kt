package com.amsterdam.cutetudee.presentation.component.chip.tast_status

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.ColorType

enum class TaskStatusUi(
    @StringRes val labelRes: Int,
    private val containerColorProvider: ColorType,
    private val contentColorProvider: ColorType
) {
    IN_PROGRESS(
        labelRes = R.string.in_progress,
        containerColorProvider = { AppTheme.color.purpleVariant },
        contentColorProvider = { AppTheme.color.purpleAccent }
    ),
    TODO(
        labelRes = R.string.todo_tab,
        containerColorProvider = { AppTheme.color.yellowVariant },
        contentColorProvider = { AppTheme.color.yellowAccent }
    ),

    DONE(
        labelRes = R.string.done,
        containerColorProvider = { AppTheme.color.greenVariant },
        contentColorProvider = { AppTheme.color.greenAccent }
    );

    val containerColor: Color
        @Composable get() = containerColorProvider()

    val contentColor: Color
        @Composable get() = contentColorProvider()
}