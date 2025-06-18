package com.amsterdam.cutetudee.presentation.component.chip.priority

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.ColorType

enum class PriorityUi(
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
    private val selectedContainerColorProvider: ColorType,
) {
    LOW(
        labelRes = R.string.priority_low,
        iconRes = R.drawable.trade_down_icon,
        selectedContainerColorProvider = { AppTheme.color.greenAccent }
    ),

    MEDIUM(
        labelRes = R.string.priority_medium,
        iconRes = R.drawable.alert_icon,
        selectedContainerColorProvider = { AppTheme.color.yellowAccent }
    ),

    HIGH(
        labelRes = R.string.priority_high,
        iconRes = R.drawable.flag_icon,
        selectedContainerColorProvider = { AppTheme.color.pinkAccent }
    );

    val selectedContainerColor: Color
        @Composable get() = selectedContainerColorProvider()
}