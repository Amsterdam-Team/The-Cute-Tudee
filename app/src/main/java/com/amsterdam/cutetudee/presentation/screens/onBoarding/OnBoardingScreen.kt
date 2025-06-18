package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.onboarding.OnboardingCard
import com.amsterdam.cutetudee.presentation.component.onboarding.OnboardingImage
import com.amsterdam.cutetudee.presentation.component.OnboardingIndicators
import com.amsterdam.cutetudee.presentation.component.VerticalSpacer
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(onBoardingViewModel: OnBoardingViewModel = koinViewModel()
                     ,navController: NavController,
                     onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit) {
    val state = onBoardingViewModel.uiState.collectAsState()
    OnboardingContent(
        state = state.value,
        onFinishClicked = onBoardingViewModel::onFinishClicked
    )
    if (state.value.isOnboardingFinished)
        navController.navigate(Screen.Home)
    if (state.value.error != null)
        onShowSnackBar(stringResource(id = state.value.error!!), CustomSnackBarStatus.Failure)
}

@Composable
fun OnboardingContent(
    state: OnboardingUiState,
    onFinishClicked: () -> Unit,
    ) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.surface)
            .background(AppTheme.color.overlay)
            .navigationBarsPadding()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
            painter = painterResource(AppTheme.images.onBoardingBackground),
            contentDescription = null
        )
        val pagerState = rememberPagerState(pageCount = { state.onboardingScreenDataList.size })
        val isLastScreen = state.onboardingScreenDataList.size - 1 == pagerState.currentPage
        AnimatedSkipText(isVisible = !isLastScreen){
            scope.launch {
                pagerState.animateScrollToPage(state.onboardingScreenDataList.size - 1)
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalArrangement =
                    Arrangement.Bottom
            ) {
                OnboardingImage(painter = painterResource(state.onboardingScreenDataList[page].imageId))
                OnboardingCard(
                    modifier = Modifier.padding(top = 32.dp),
                    title = stringResource(id = state.onboardingScreenDataList[page].title),
                    description = stringResource(id = state.onboardingScreenDataList[page].description),
                    onButtonClick = {
                        if (isLastScreen)
                            onFinishClicked()
                        else
                            scope.launch {
                                pagerState.animateScrollToPage(page + 1)
                            }
                    }
                )
                VerticalSpacer(32.dp)
                OnboardingIndicators(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    currentIndicator = pagerState.currentPage
                )
            }
        }

    }
}

@Composable
private fun BoxScope.AnimatedSkipText(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier =
            Modifier
                .zIndex(10f)
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick)
                .align(Alignment.TopStart),
        visible = isVisible,
        enter = slideInHorizontally(
            tween(durationMillis = 1000),
            initialOffsetX = { it - 1000 }),
        exit =
            slideOutHorizontally(tween(durationMillis = 1000), targetOffsetX = {
                it - 1000
            }),
    ) {
        Text(
            modifier = modifier, text = stringResource(
                id = R.string.skip
            ),
            style = AppTheme.textStyle.label.large,
            color = AppTheme.color.primary
        )
    }
}

@PreviewLightDark()
@Composable
private fun OnboardingScreenPreview() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        OnboardingContent(OnboardingUiState(), {})
    }
}