package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri

interface CategoryInteractionListener {
    fun onFABClicked()

}

interface CategoryAddInteractionListener {
    fun onAddCategoryClicked()
    fun onUpdateCategoryImage(uri: Uri)
    fun onUpdateCategoryTextValue(text: String)
    fun onCancelAddCategoryClicked()
    fun onDismissAddSheet()
}