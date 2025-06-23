package com.amsterdam.cutetudee.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class UriToBitmapString(
    private val appCtx: Context
) {
    suspend fun uriToBase64(uri: Uri): String = withContext(Dispatchers.IO) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(appCtx.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(appCtx.contentResolver, uri)
            }

            ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun bitmapToUri(userBitmap: Bitmap?): Uri = withContext(Dispatchers.IO) {
        try {
            val bitmap = userBitmap ?: return@withContext Uri.EMPTY

            val filename = "temp_image_${System.currentTimeMillis()}.png"
            val file = File(appCtx.filesDir, filename)

            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            return@withContext FileProvider.getUriForFile(
                appCtx,
                "${appCtx.packageName}.fileprovider",
                file
            )

        } catch (e: Exception) {
            e.printStackTrace()
            e.localizedMessage?.let { Log.d("HERE", it) }
            Uri.EMPTY
        }
    }
}