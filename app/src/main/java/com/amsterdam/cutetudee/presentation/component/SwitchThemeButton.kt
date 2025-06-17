package com.amsterdam.cutetudee.presentation.component

import android.text.Layout
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCircleExample() {
    var isClicked by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .background(Color.Red)
            .clickable { isClicked = !isClicked },
        contentAlignment = Alignment.TopStart
    ) {
   SwitchButton()
    }
}

@Preview
@Composable
fun p() {
    AnimatedCircleExample()
}

@Composable
fun SwitchButton(

) {
    var isClicked by remember { mutableStateOf(false) }

    val offsetX by animateDpAsState(
        targetValue = if (isClicked) 0.dp else 50.dp,
        animationSpec = tween(durationMillis = 400)
    )
    val offsetY by animateDpAsState(
        targetValue = if (isClicked) 5.dp else 15.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val color by animateColorAsState(
        targetValue = if (isClicked) Color.Black else  Color(0xFF548EFE) ,
        animationSpec = tween(durationMillis = 400)
    )
    val size by animateDpAsState(
        targetValue = if (isClicked) 25.dp else 20.dp,
        animationSpec = tween(durationMillis = 600)
    )
    val colorCircle by animateColorAsState(
        targetValue = if (isClicked) Color.White else  Color(0xFFFF9800),
        animationSpec = tween(durationMillis = 400)
    )
    val p by animateDpAsState(
        targetValue = if(isClicked) 0.dp else 28.dp
    )
   Box(){
       Row(
           modifier = Modifier.padding(24.dp)
               .height(36.dp)
               .clip(RoundedCornerShape(100.dp))
               .background(color)
               .padding(2.dp)
               .clickable{ isClicked = !isClicked }
               .align(Alignment.CenterStart)
       ) {
           Box(
               modifier = Modifier
                   .size(32.dp)
                   .clip(CircleShape)
                   .offset(x=p)
                   .background(colorCircle)
           )
//           Box(
//               modifier = Modifier
//                   .offset(x = offsetX, y = offsetY)
//                   .size(32.dp)
//                   .clip(CircleShape)
//                   .background(colorCircle)
//           )
//           Box(
//               modifier = Modifier
//                   .offset(x = offsetX, y = offsetY)
//                   .size(size)
//                   .clip(CircleShape)
//                   .background(colorCircle)
//
//           )

       }
   }
}