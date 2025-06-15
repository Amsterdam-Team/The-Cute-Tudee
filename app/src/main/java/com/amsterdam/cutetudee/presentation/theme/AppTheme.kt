package com.amsterdam.cutetudee.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.amsterdam.cutetudee.presentation.theme.colors.CuteTudeeAppColors
import com.amsterdam.cutetudee.presentation.theme.colors.CuteTudeeLocalColors
import com.amsterdam.cutetudee.presentation.theme.textStyle.LocalTudeeTextStyle
import com.amsterdam.cutetudee.presentation.theme.textStyle.TudeeTextStyle

object AppTheme {
    val color: CuteTudeeAppColors
        @Composable @ReadOnlyComposable get() = CuteTudeeLocalColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

}