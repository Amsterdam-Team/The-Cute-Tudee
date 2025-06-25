package com.amsterdam.cutetudee.presentation.screens.categoryDetails

sealed class CategoryDetailsEffect {
    data object ShowEditSnackBar: CategoryDetailsEffect()
    data object ShowDeleteSnackBar: CategoryDetailsEffect()
    data object ShowError: CategoryDetailsEffect()
    data object NavigateBack: CategoryDetailsEffect()
}