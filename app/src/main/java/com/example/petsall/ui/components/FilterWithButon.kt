package com.example.petsall.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.R
import com.example.petsall.ui.theme.Purple500


@Composable
fun FilterVet(
    list: List<String> = listOf(),
    title: String = "",
    counFilters: Int = 0,
    selectedSectors: List<String?> = listOf(),
    onClickSector: (String) -> Unit = {},
    onClickFilter: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 12.sp
            )
            LazyRow(modifier = Modifier) {
                items(list) { sector ->
                    ChipCard(
                        sector = sector, isSelected = selectedSectors.contains(sector)
                    ) {
                        onClickSector(sector)
                    }

                }
            }
        }

        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                IconButton(
                    onClick = { onClickFilter.invoke() },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "filter",
                        tint = Color.Gray
                    )
                }
            }
            if (counFilters != 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = counFilters
                            .toString(), // Reemplaza con el número de elementos que desees mostrar
                        fontWeight = FontWeight.Bold,
                        color = Purple500,
                        fontSize = 8.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}