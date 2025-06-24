package com.amsterdam.cutetudee.presentation.component.AddCategoryBottomSheet

import android.net.Uri

interface CategoryInteractionListener {
    fun deleteCategory()
    fun upsertCategory()
    fun onDismiss()
    fun onTextValueChange(text: String)
    fun onImageSelected(image: Uri)
}