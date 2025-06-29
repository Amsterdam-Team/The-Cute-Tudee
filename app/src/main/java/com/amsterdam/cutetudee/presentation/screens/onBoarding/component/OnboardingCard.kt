package com.amsterdam.cutetudee.presentation.screens.onBoarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.VerticalSpacer
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun OnboardingCard(
    modifier: Modifier = Modifier, title: String, description: String, onButtonClick: () -> Unit
) {
    Box(
        modifier.padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(AppTheme.color.onPrimaryCard, RoundedCornerShape(32.dp))
                .border(
                    width = 1.dp,
                    color = AppTheme.color.onPrimaryStroke,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 43.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                style = AppTheme.textStyle.title.medium,
                minLines = 2,
                color = AppTheme.color.title
            )
            VerticalSpacer(16.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                textAlign = TextAlign.Center,
                style = AppTheme.textStyle.body.medium,
                color = AppTheme.color.body,
                minLines = 3
            )
        }
        val layoutDirection = LocalLayoutDirection.current

        val floatingActionButtonRotate = if (layoutDirection == LayoutDirection.Rtl) 180f else 0f
        CustomFloatingActionButton(
            modifier = Modifier
                .offset(y = 32.dp)
                .align(Alignment.BottomCenter)
                .rotate(floatingActionButtonRotate),
            iconDrawable = painterResource(id = R.drawable.arrow_right_double_icon),
            onClick = onButtonClick,
            isLoading = false,
            iconDescription = stringResource(R.string.next_button_onboarding_description)
        )
    }
}

@ThemeAndLocalePreviews()
@Composable
private fun OnboardingCardPreviewDark() {
    CuteTudeeTheme() {
        OnboardingCard(
            title = stringResource(R.string.onboarding_title_one),
            description = stringResource(R.string.onboarding_description_one)
        ) {}
    }
}
