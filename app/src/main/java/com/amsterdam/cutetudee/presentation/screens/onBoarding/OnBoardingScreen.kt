package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun OnBoardingScreen(navController: NavController) {
    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
        Text("OnBoarding Screen", color = AppTheme.color.title)
        Box(
            modifier = Modifier
                .padding(30.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Green)
                .width(100.dp)
                .height(60.dp)
                .align(Alignment.BottomEnd)
                .clickable{
                    navController.navigate(Screen.Home)
                },
            contentAlignment = Center
        ) {
            Text("Navigate")
        }
    }
}