package com.amsterdam.cutetudee.presentation.screens.category.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dashedBorder

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    image: Uri = Uri.EMPTY,
    onImageSelected: (Uri) -> Unit = {}
) {
    val context = LocalContext.current
    var currentImage by remember { mutableStateOf(image) }

    LaunchedEffect(image) {
        currentImage = image
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                currentImage = uri
                onImageSelected(uri)
            }
        }
    )

    val hasImage = (currentImage != Uri.EMPTY)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .size(112.dp)
            .background(AppTheme.color.surface)
            .clickable { galleryLauncher.launch(arrayOf("image/*")) }
            .dashedBorder(
                color = AppTheme.color.stroke,
                strokeWidth = 1.dp,
                dashLength = 6.dp,
                cornerRadius = 16.dp
            )
            .padding(if (hasImage) 0.dp else 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasImage) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(currentImage)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppTheme.color.surfaceHigh)
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_task_icon),
                        contentDescription = "edit icon",
                        tint = AppTheme.color.secondary,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        } else {
            Icon(
                painter = painterResource(R.drawable.add_category_image_icon),
                contentDescription = "upload",
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(24.dp),
                tint = AppTheme.color.hint
            )
            Text(
                text = stringResource(R.string.upload),
                color = AppTheme.color.hint,
                style = AppTheme.textStyle.label.medium
            )
        }
    }

}

@ThemeAndLocalePreviews
@Preview(showBackground = true)
@Composable
private fun ImagePickerPreview() {
    CuteTudeeTheme(isDarkTheme = false) {
        ImagePicker()
    }
}