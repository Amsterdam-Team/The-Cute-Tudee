package com.amsterdam.cutetudee.presentation.screens.category

sealed class CategoryEffect {
    data object ShowAddSnackBar : CategoryEffect()
    data object ShowDuplicateNameSnackBar : CategoryEffect()
    data object ShowError : CategoryEffect()
}