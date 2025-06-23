package com.amsterdam.cutetudee.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.navigation.LocalNavController
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current
    val state = splashViewModel.state.collectAsState()
    SplashContent()
    LaunchedEffect(
        key1 = state.value,
    ) {
        if (state.value != null) {
            delay(3000)
            navController.popBackStack()
            if (state.value == true) {
                navController.navigate(Screen.Home)
            } else {
                navController.navigate(Screen.OnBoarding)
            }

        }
    }
}

@Composable
fun SplashContent() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.overlay),
    ) {
        Image(
            painter = painterResource(AppTheme.images.onBoardingBackground),
            contentDescription = "Splash Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text(
                    text = stringResource(R.string.app_name),
                    style = AppTheme.textStyle.appLogo.medium,
                    color = Color.White,
                    modifier = Modifier.zIndex(10f)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = AppTheme.textStyle.appLogoWithBorder.medium,
                    color = AppTheme.color.primary,
                )
            }
        }

    }
}


@ThemeAndLocalePreviews
@Composable
private fun SplashScreenPreview() {
    CuteTudeeTheme { SplashContent() }
}