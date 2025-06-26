package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.imageModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryImage: Uri,
    isAddedByUser: Boolean,
    onClick: (() -> Unit)? = null,
    isSelected : Boolean = false
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(AppTheme.color.surfaceHigh,CircleShape)
                .then(
                    if (onClick != null)
                        Modifier.combinedClickable(remember { MutableInteractionSource() }, ripple()) {
                            onClick()
                        }
                    else Modifier
                ),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier.clip(CircleShape)
                    .then(if (isAddedByUser) Modifier else Modifier.size(32.dp)),
                model = imageModel(context, categoryImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
           this@Column.AnimatedVisibility(isSelected,modifier = Modifier.align(Alignment.TopEnd).offset(x = (-8).dp, y = 8.dp)) {
                SelectedBadge()
            }
        }
        Text(
            text = categoryName,
            style = AppTheme.textStyle.label.small,
            color = AppTheme.color.body,
            maxLines = 1
        )
    }
}

@Composable
private fun SelectedBadge(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(20.dp).clip(CircleShape),
        painter = painterResource(id = R.drawable.correct_badge_icon),
        contentDescription = "selected category icon",
    )
}

@ThemeAndLocalePreviews
@Composable
private fun CategoryItemPreview() {
    CuteTudeeTheme {
        CategoryItem(
            categoryName = stringResource(R.string.education),
            categoryImage = Uri.EMPTY,
            modifier = Modifier.background(AppTheme.color.surface).width(100.dp),
            isAddedByUser = false,
            isSelected = true
        )
    }
}