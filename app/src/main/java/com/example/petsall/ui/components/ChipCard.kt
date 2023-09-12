package com.example.petsall.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.ui.theme.Black
import com.example.petsall.ui.theme.BtnBlue
import com.example.petsall.ui.theme.White
import com.example.petsall.utils.capitalizeName

@Composable
fun ChipCard(sector: String = "", isSelected: Boolean, onClick: (String) -> Unit = {}) {
    val colorBackground = if (isSelected) BtnBlue else White
    val colorTxt = if (isSelected) White else Black

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .toggleable(value = isSelected, onValueChange = {
                onClick(sector)
            })
            .padding(horizontal = 4.dp), shape = RoundedCornerShape(16.dp), elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.background(colorBackground)
        ) {
            Text(
                text = sector.capitalizeName(),
                fontSize = 14.sp,
                color = colorTxt,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }
    }
}