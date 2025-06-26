package com.amsterdam.cutetudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AppTheme.textStyle.body.medium,
    hintText: String = "",
    maxLines: Int = 1,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMsg: String = "",
    maxCharacters: Int = Int.MAX_VALUE,
    @DrawableRes leadingIcon: Int? = null,
    borderColor: Color = AppTheme.color.stroke,
    borderErrorColor: Color = AppTheme.color.error,
    borderFocusedColor: Color = AppTheme.color.primary,
    onValueChange: (String) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val textFieldHeight = getTextFieldHeight(maxLines)
    val canShowMaxCharacters = maxCharacters - text.length < 5

    val currentBorderColor by animateColorAsState(
        targetValue = if (isError) borderErrorColor else if (isFocused) borderFocusedColor else borderColor
    )

    val messageColor by animateColorAsState(
        targetValue = if (isError) AppTheme.color.error
        else if (canShowMaxCharacters) AppTheme.color.body
        else AppTheme.color.primary
    )

    val message =
        if (isError) errorMsg
        else if (canShowMaxCharacters) "${text.length}/$maxCharacters"
        else ""

    Column {
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxCharacters) onValueChange(it)
                else if (it.length > text.length + 1) onValueChange(it.substring(0, maxCharacters))
            },
            maxLines = maxLines,
            enabled = isEnabled,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = currentBorderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = style.copy(color = AppTheme.color.body),
            singleLine = maxLines == 1,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 56.dp)
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .height(textFieldHeight),
                    verticalAlignment = Alignment.Top
                ) {
                    if (leadingIcon != null) {
                        val imageColor by animateColorAsState(
                            targetValue = if (text.isEmpty()) AppTheme.color.hint else AppTheme.color.body
                        )
                        LeadingIcon(leadingIcon, imageColor)
                        VerticalDivider()
                    }
                    InnerTextFieldWithHint(innerTextField, text, hintText, style)
                }
            }
        )
        AnimatedMessage(isError, canShowMaxCharacters, message, style, messageColor)
    }
}

@Composable
private fun RowScope.InnerTextFieldWithHint(
    innerTextField: @Composable (() -> Unit),
    text: String,
    hintText: String,
    style: TextStyle
) {
    Box(
        Modifier
            .padding(vertical = 5.dp)
            .padding(top = (if (LocalLayoutDirection.current == LayoutDirection.Rtl) 0 else 3).dp)
            .weight(1f),
        contentAlignment = Alignment.CenterStart
    ) {
        innerTextField()
        if (text.isEmpty()) {
            Text(
                text = hintText,
                style = style,
                fontSize = 16.sp,
                color = AppTheme.color.hint,
            )
        }
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .padding(horizontal = 12.dp, vertical = 3.dp)
            .size(1.dp, 30.dp)
            .background(AppTheme.color.stroke)
    )
}

@Composable
private fun LeadingIcon(leadingIcon: Int, imageColor: Color) {
    Image(
        imageVector = ImageVector.vectorResource(id = leadingIcon),
        contentDescription = null,
        colorFilter = ColorFilter.tint(imageColor),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .size(24.dp)
    )
}

@Composable
private fun ColumnScope.AnimatedMessage(
    isError: Boolean,
    canShowMaxCharacters: Boolean,
    message: String,
    style: TextStyle,
    messageColor: Color
) {
    AnimatedVisibility(
        visible = isError || canShowMaxCharacters
    ) {
        Text(
            text = message,
            style = style,
            fontSize = 12.sp,
            color = messageColor,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 8.dp)
                .padding(top = 4.dp)
                .animateContentSize()
        )
    }
}

@Composable
private fun getTextFieldHeight(maxLines: Int): Dp = (20 * maxLines + 6 * (maxLines - 1)).dp

@ThemeAndLocalePreviews
@Composable
private fun CustomTextFieldPreview() {
    CuteTudeeTheme {
        Column(
            Modifier
                .background(AppTheme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                "",
                hintText = stringResource(R.string.task_title_hint),
                leadingIcon = R.drawable.user_icon,
                maxLines = 1,
            )
            CustomTextField(
                "",
                hintText = stringResource(R.string.task_title_hint),
                leadingIcon = R.drawable.user_icon,
                maxLines = 1,
                isError = true,
                errorMsg = stringResource(R.string.error_invalid_task_input)
            )
            CustomTextField(
                "This is a test title for field",
                hintText = stringResource(R.string.task_title_hint),
                leadingIcon = R.drawable.user_icon,
                maxLines = 1,
                maxCharacters = 32
            )
            CustomTextField(
                "",
                hintText = stringResource(R.string.task_title_hint),
                leadingIcon = R.drawable.user_icon,
                maxLines = 3,
            )
            CustomTextField(
                "",
                hintText = stringResource(R.string.task_title_hint),
                maxLines = 3,
            )
        }
    }
}