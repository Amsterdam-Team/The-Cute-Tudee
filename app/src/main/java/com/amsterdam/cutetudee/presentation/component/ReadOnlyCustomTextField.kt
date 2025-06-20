package com.amsterdam.cutetudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun ReadOnlyCustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AppTheme.textStyle.body.medium,
    maxLines: Int = 1,
    @DrawableRes leadingIcon: Int? = null,
    borderColor: Color = AppTheme.color.body,
    onClick: () -> Unit = {}
) {
    val textFieldHeight = getTextFieldHeight(maxLines)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 56.dp)
                .padding(horizontal = 12.dp, vertical = 13.dp)
                .height(textFieldHeight)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.Top
        ) {
            if (leadingIcon != null) {
                val imageColor = AppTheme.color.body
                Image(
                    imageVector = ImageVector.vectorResource(id = leadingIcon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(imageColor),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                )
                Box(
                    Modifier
                        .padding(horizontal = 12.dp)
                        .size(1.dp, 30.dp)
                        .background(AppTheme.color.stroke)
                )
            }
            Box(
                Modifier.padding(vertical = 5.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = text,
                    style = style,
                    fontSize = 16.sp,
                    color = AppTheme.color.body,
                    maxLines = maxLines
                )
            }
        }
    }
}

@Composable
private fun getTextFieldHeight(maxLines: Int): Dp = (20 * maxLines + 6 * (maxLines - 1)).dp

@ThemeAndLocalePreviews
@Composable
fun ReadOnlyCustomTextFieldPreview() {
    CuteTudeeTheme {
        ReadOnlyCustomTextField(text = "Text", maxLines = 1, leadingIcon = R.drawable.user_icon)
    }
}