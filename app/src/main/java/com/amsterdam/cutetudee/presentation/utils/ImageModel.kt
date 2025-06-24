package com.amsterdam.cutetudee.presentation.utils

import android.content.Context
import android.net.Uri
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.amsterdam.cutetudee.R

fun imageModel(context: Context, image: Uri): ImageRequest {
    return ImageRequest.Builder(context)
        .placeholder(R.drawable.placeholder)
        .data(image)
        .error(R.drawable.fallback)
        .crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}