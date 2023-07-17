package com.example.petsall.ui.components.bottom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderBottomSheet(
    titleText: String,
    imageResourceId: ImageVector,
    onClickClose: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = titleText,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 48.dp),
                textAlign = TextAlign.Center,

                )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = imageResourceId, contentDescription = "Image", modifier = Modifier
                .padding(end = 13.dp)
                .clickable {
                    onClickClose.invoke()
                }
            )
        }
    }
}