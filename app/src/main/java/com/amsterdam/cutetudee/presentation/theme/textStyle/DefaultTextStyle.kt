package com.amsterdam.cutetudee.presentation.theme.textStyle

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.amsterdam.cutetudee.R

val nunito = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semi_bold, FontWeight.SemiBold),
    Font(R.font.nunito_medium, FontWeight.Medium),
)

val cherryBomb = FontFamily(
    Font(R.font.cherrybomb_regular, FontWeight.Normal)
)

val defaultTextStyle = TudeeTextStyle(

    headLine = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 30.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )
    ),

    title = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    ),

    body = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 17.sp
        )
    ),

    label = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 19.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 17.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    ),

    appName = SizedTextStyle(
        medium = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 18.sp
        ),
        large = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 18.sp
        ),
        small = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 18.sp
        ),
    ),

    appLogo = SizedTextStyle(
        medium = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
        ),
        large = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
        ),
        small = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
        ),
    ),

    appLogoWithBorder = SizedTextStyle(
        medium = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
            drawStyle = Stroke(
                width = 10.sp.value,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        ),
        large = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
            drawStyle = Stroke(
                width = 10.sp.value,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        ),
        small = TextStyle(
            fontFamily = cherryBomb,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-2.4f).sp,
            drawStyle = Stroke(
                width = 10.sp.value,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        ),
    ),
)