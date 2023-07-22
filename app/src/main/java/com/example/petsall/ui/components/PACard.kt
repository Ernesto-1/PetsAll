package com.example.petsall.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.R

@Preview
@Composable
fun PACard(iconCard: Int = R.drawable.request,txtCard: String = "",colorIcon: Color = Color(0xff78CEFF)){
    Card(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
        .height(100.dp)
        .fillMaxWidth()
        .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(12.dp))
        .clickable(
            onClick = {
            }),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White) {
        Row() {
            Icon(painter = painterResource(id = iconCard), contentDescription = "", modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 15.dp)
                .fillMaxHeight()
                .width(45.dp), tint = colorIcon)
            Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center) {
                Text(text = txtCard, modifier = Modifier.fillMaxWidth(),  fontSize = 15.sp)
            }
        }
    }
}

@Preview
@Composable
fun PACard2(modifier: Modifier = Modifier,iconCard: Int = R.drawable.request,txtCard: String = "gvbhnjmk,ln",colorIcon: Color = Color(0xff78CEFF)){
    Card(modifier = modifier
        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
        .height(100.dp)
        .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(12.dp))
        .clickable(
            onClick = {
            }),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(id = iconCard), contentDescription = "", modifier = Modifier
                .padding(vertical = 10.dp).height(45.dp)
                .fillMaxWidth(), tint = colorIcon)
                Text(text = txtCard,  fontSize = 12.sp)
        }
    }
}
