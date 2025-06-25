package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.amsterdam.cutetudee.R

data class OnboardingUiState(
    val isOnboardingFinished: Boolean = false,
    @StringRes val error: Int? = null,
    val onboardingScreenDataList: List<OnboardingScreenData> = listOf<OnboardingScreenData>(
        OnboardingScreenData(
            R.drawable.onboarding_image_1,
            R.string.onboarding_title_one,
            R.string.onboarding_description_one
        ),
        OnboardingScreenData(
            R.drawable.onboarding_image_2,
            R.string.onboarding_title_two,
            R.string.onboarding_description_two
        ),
        OnboardingScreenData(
            R.drawable.onboarding_image_3,
            R.string.onboarding_title_three,
            R.string.onboarding_description_three
        )
    )
)

data class OnboardingScreenData(
    @DrawableRes val imageId: Int = R.drawable.onboarding_image_1,
    @StringRes val title: Int = R.string.onboarding_title_one,
    @StringRes val description: Int = R.string.onboarding_description_one,
)