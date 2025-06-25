package com.amsterdam.cutetudee.presentation.screens.home

sealed class HomeEffect {
    data object ShowTaskEditedSuccessfullySnackBar : HomeEffect()

    data object ShowTaskEditedFailedSnackBar : HomeEffect()

    data object ShowTaskAddedSuccessfullySnackBar : HomeEffect()

    data object ShowTaskAddedFailedSnackBar : HomeEffect()
}
