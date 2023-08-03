package com.example.petsall.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.ui.theme.Purple200

@Preview(showBackground = true)
@Composable
fun listSpecialized(list: List<*>? = null) {
    if (!list.isNullOrEmpty()) {
        Text(
            text = "Especialidades",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Purple200
        )
        Column(Modifier.padding(vertical = 12.dp)) {
            GSPRMFlexLayout(verticalGap = 16.dp, horizontalGap = 8.dp) {
                list.forEach { Specialties ->
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "• ${Specialties.toString()}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                        )
                    }
                }
            }
        }
    }

}