package com.amsterdam.cutetudee.presentation.screens.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun CategoryScreen(navController: NavController) {
    Box(Modifier.fillMaxSize() , contentAlignment = Center){
        Text("Category Screen", color = AppTheme.color.title)
    }
}