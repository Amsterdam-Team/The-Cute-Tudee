package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import com.amsterdam.cutetudee.domain.entity.Task

interface CategoryDetailsInteractionListener {
    fun onTaskStatusChanged(taskStatus: Task.Status)
    fun onEditOptionClicked(name: String, uri: Uri)
    fun onNavigateBackClicked()
}

interface CategoryEditInteractionListener {
    fun onSaveCategoryClicked()
    fun onUpdateCategoryImage(uri: Uri)
    fun onUpdateCategoryTextValue(text: String)
    fun onDeleteCategoryClicked()
    fun onCancelEditCategoryClicked()
    fun onDismissEditSheet()
}

interface CategoryDeleteConfirmationInteractionListener {
    fun onDeleteConfirmationClicked()
    fun onCancelDeleteConfirmationClicked()
    fun onDismissDeleteConfirmationSheet()
}