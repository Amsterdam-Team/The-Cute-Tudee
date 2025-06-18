package com.amsterdam.cutetudee.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.amsterdam.cutetudee.presentation.theme.colors.CuteTudeeAppColors
import com.amsterdam.cutetudee.presentation.theme.colors.LocalCuteTudeeLocalColors
import com.amsterdam.cutetudee.presentation.theme.images.CuteTudeeAppImages
import com.amsterdam.cutetudee.presentation.theme.images.LocalCuteTudeeLocalImages
import com.amsterdam.cutetudee.presentation.theme.textStyle.LocalTudeeTextStyle
import com.amsterdam.cutetudee.presentation.theme.textStyle.TudeeTextStyle

object AppTheme {
    val color: CuteTudeeAppColors
        @Composable @ReadOnlyComposable get() = LocalCuteTudeeLocalColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

    val images: CuteTudeeAppImages
        @Composable @ReadOnlyComposable get() = LocalCuteTudeeLocalImages.current
}