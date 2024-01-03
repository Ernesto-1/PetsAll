package com.example.petsall.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.R
import com.example.petsall.data.remote.model.VaccineDataClass
import com.example.petsall.ui.theme.*
import com.example.petsall.utils.capitalizeName
import com.example.petsall.utils.convertTimestampToString
import com.google.firebase.Timestamp

@Preview(showBackground = true)
@Composable
fun CardVaccine(modifier: Modifier = Modifier, data: VaccineDataClass? = VaccineDataClass()) {
    val colorStatus: Color = when (data?.status) {
        "pendiente" -> statusEarring
        "vencido" -> statusDefeated
        "vigente" -> statusCurrent
        else -> statusEarring
    }
    val date = convertTimestampToString(data?.dateVaccine as Timestamp)
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(116.dp)
            .fillMaxWidth()
            .clickable(
                enabled = false,
                onClick = {}
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
        ) {
            Box(
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxHeight()
                    .width(81.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (data.type == "vacuna") painterResource(id = R.drawable.vaccine) else painterResource(id = R.drawable.medicines),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    val contrastColor = getContrastingColor(colorStatus)

                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = colorStatus,
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 12.dp,
                                    bottomStart = 12.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                            .height(27.dp)
                    ) {
                        Text(
                            text = data.status.capitalizeName(),
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 3.dp
                            ),
                            maxLines = 1,
                            style = MaterialTheme.typography.body2.copy(color = contrastColor),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = data.vaccine,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Purple200
                    )
                    Text(
                        text = ("Fecha: $date"),
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = 12.sp,
                        style = TextStyle.Default
                    )
                    Text(
                        text = "Ced. Prof." + data.pLicense,
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = 12.sp,
                        style = TextStyle.Default
                    )
                }

            }
        }
    }
}

@Composable
private fun getContrastingColor(backgroundColor: Color): Color {
    val isBackgroundLight = backgroundColor.luminance() > 0.5f

    // Ajustar los componentes RGB del color de texto para hacerlo más oscuro
    val textRed = if (isBackgroundLight) (backgroundColor.red * 0.7f).coerceIn(
        0f,
        1f
    ) else backgroundColor.red
    val textGreen = if (isBackgroundLight) (backgroundColor.green * 0.7f).coerceIn(
        0f,
        1f
    ) else backgroundColor.green
    val textBlue = if (isBackgroundLight) (backgroundColor.blue * 0.7f).coerceIn(
        0f,
        1f
    ) else backgroundColor.blue

    return Color(textRed, textGreen, textBlue)
}
