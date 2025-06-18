package com.amsterdam.cutetudee.presentation.screens.onBoarding

import com.amsterdam.cutetudee.R

data class OnboardingUiState(
    val isOnboardingFinished : Boolean = false,
    val onboardingScreenState: List<OnboardingScreenState> = listOf<OnboardingScreenState>(
        OnboardingScreenState(
            R.drawable.onboarding_image_1,
            R.string.onboarding_title_one,
            R.string.onboarding_description_one),
        OnboardingScreenState(
            R.drawable.onboarding_image_2,
            R.string.onboarding_title_two,
            R.string.onboarding_description_two),
        OnboardingScreenState(
            R.drawable.onboarding_image_3,
            R.string.onboarding_title_three,
            R.string.onboarding_description_three)
    )
)

data class OnboardingScreenState(
    val imageId : Int = R.drawable.onboarding_image_1,
    val title : Int = R.string.onboarding_title_one,
    val description : Int = R.string.onboarding_description_one,
)