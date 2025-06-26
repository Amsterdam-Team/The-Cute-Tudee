package com.amsterdam.cutetudee.presentation.screens.categoryDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TopAppBar(
    onClickBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    withOption: Boolean = true,
    onclickOption: () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppTheme.color.surfaceHigh)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val layoutDirection = LocalLayoutDirection.current
        val icon = if (layoutDirection == LayoutDirection.Rtl)
            R.drawable.right_arrow_icon
        else
            R.drawable.left_arrow_icon
        circleButton(
            iconRes =icon,
            onClick = onClickBack,
            background = AppTheme.color.surfaceHigh,
            iconColor = AppTheme.color.body,
            borderColor = AppTheme.color.stroke,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = AppTheme.textStyle.title.large,
                maxLines = 1,
                color = AppTheme.color.title,
                overflow = TextOverflow.Ellipsis
            )
            label?.let {
                Text(
                    text = label,
                    style = AppTheme.textStyle.label.small,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (withOption) {
            circleButton(
                iconRes = R.drawable.edit_task_icon,
                onClick = onclickOption,
                background = AppTheme.color.surfaceHigh,
                iconColor = AppTheme.color.body,
                borderColor = AppTheme.color.stroke,
            )
        }
    }
}
@Composable
private fun circleButton(
    iconRes: Int,
    onClick: () -> Unit = {},
    background: Color = AppTheme.color.surfaceHigh,
    borderColor: Color = AppTheme.color.stroke,
    iconColor: Color = AppTheme.color.body,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(background)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = iconColor
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TopAppBarPreview() {
    CuteTudeeTheme {
        TopAppBar(
            onClickBack = {},
            onclickOption = {},
            modifier = Modifier,
            withOption = true,
            title = "Tasks",
            label = "32 Task"
        )
    }
}