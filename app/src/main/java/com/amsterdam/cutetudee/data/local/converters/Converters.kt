package com.amsterdam.cutetudee.data.local.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import androidx.core.net.toUri

class Converters {
    @TypeConverter
    fun fromUriToString(uri: Uri) = uri.toString()


    @TypeConverter
    fun fromStringToUri(stringUri: String) = stringUri.toUri()
}