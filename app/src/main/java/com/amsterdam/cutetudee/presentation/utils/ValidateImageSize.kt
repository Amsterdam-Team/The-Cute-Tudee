package com.amsterdam.cutetudee.presentation.utils

import android.content.Context
import android.net.Uri

class ValidateImageSize(
    private val appCtx: Context
) {
    operator fun invoke(uri: Uri): Boolean{
        val inputStream = appCtx.contentResolver.openInputStream(uri)
        val size = inputStream?.available() ?: 0
        if (size > 5_000_000){
            inputStream?.close()
            return false
        }
        inputStream?.close()
        return true
    }
}