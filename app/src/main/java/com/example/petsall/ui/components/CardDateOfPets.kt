package com.example.petsall.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petsall.R

@Preview
@Composable
fun CardDateOfPet(data: Map<String, Any>? = mapOf(), id: String = "" ,isSelected: Boolean = false,onClick: () -> Unit = {}) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent
    if (data?.isNotEmpty() == true) {
        Column(modifier = Modifier
            .wrapContentSize()
            .clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .border(1.dp, borderColor, shape = RoundedCornerShape(50.dp)),
                elevation = if (isSelected) 4.dp else 0.dp, shape = RoundedCornerShape(50.dp)
            ) {
                Column(modifier = Modifier.border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(50.dp)), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (data["ImgUrl"].toString() != ""){
                        AsyncImage(
                            model = data["ImgUrl"].toString(),
                            contentDescription = "imageFromUrl",
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.fish),
                            contentDescription = "ImageLocal",
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp) ,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            Text(text = if (isSelected) data["Nombre"].toString() else "", color = borderColor, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }

    }

}