package com.example.petsall.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.ui.theme.Purple200

@Composable
fun Filter(
    list: List<String> = listOf(),
    title: String = "",
    selectedOption: List<String?> = listOf(),
    onClickOption: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Purple200
        )

        Column(
            Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GSPRMFlexLayout(verticalGap = 16.dp, horizontalGap = 8.dp) {
                list.forEach { sector ->
                    ChipCard(
                        sector = sector, isSelected = selectedOption.contains(sector)
                    ) {
                        onClickOption(sector)
                    }
                }
            }
        }
    }
}