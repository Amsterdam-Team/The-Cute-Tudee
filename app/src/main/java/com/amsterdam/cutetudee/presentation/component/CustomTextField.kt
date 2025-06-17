package com.amsterdam.cutetudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AppTheme.textStyle.body.medium,
    hintText: String = "",
    maxLines: Int = 1,
    @DrawableRes leadingIcon: Int? = null,
    onValueChange: (String) -> Unit = {}
) {
    var textValue by rememberSaveable { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    val textFieldHeight = (20 * maxLines + 6 * (maxLines - 1)).dp

    val borderColor = if (isFocused) {
        AppTheme.color.primary
    } else {
        AppTheme.color.body
    }

    BasicTextField(
        value = textValue,
        onValueChange = { textValue = it; onValueChange(it) },
        maxLines = maxLines,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        textStyle = style,
        singleLine = maxLines == 1,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 56.dp)
                    .padding(horizontal = 12.dp, vertical = 13.dp)
                    .height(textFieldHeight),
                verticalAlignment = Alignment.Top
            ) {
                if (leadingIcon != null) {
                    val imageColor =
                        if (textValue.isEmpty()) AppTheme.color.hint else AppTheme.color.body
                    Image(
                        imageVector = ImageVector.vectorResource(id = leadingIcon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(imageColor),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp)
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
                    innerTextField()
                    if (textValue.isEmpty()) {
                        Text(
                            text = hintText,
                            style = style,
                            fontSize = 16.sp,
                            color = AppTheme.color.hint,
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CustomTextFieldPreview() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextField("", hintText = "Hint", leadingIcon = R.drawable.user_icon, maxLines = 1)
        CustomTextField("", hintText = "Hint", leadingIcon = R.drawable.user_icon, maxLines = 3)
        CustomTextField("", hintText = "Hint", maxLines = 3)
    }
}