package com.amsterdam.cutetudee.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.toBase46eString(): String {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


fun String.toBitmap(): Bitmap {
    val encodeByte = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}