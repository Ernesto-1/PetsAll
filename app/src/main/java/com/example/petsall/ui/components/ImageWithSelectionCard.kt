package com.example.petsall.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImageWithSelection(
    painter: Painter,
    modifier: Modifier,
    contentDescription: String?,
    isSelected: Boolean,
    pet: String = "",
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
                .clickable(onClick = onClick),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {

            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(12.dp)
                    .size(80.dp)
            )


        }
        Text(
            text = if (isSelected) pet else "",
            color = borderColor,
            fontSize = 12.sp,
            modifier = Modifier.wrapContentWidth(),
            textAlign = TextAlign.Center
        )
    }
}