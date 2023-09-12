package com.example.petsall.ui.components

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petsall.R
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.ui.theme.Purple500

@Preview(showBackground = true)
@Composable
fun CardVet(
    location: Location = Location(""),
    data: VetData? = null,
    id: String = "",
    onClick: () -> Unit = {}
) {
    if (data != null) {
        val locationValue = Location("location value.")
        locationValue.latitude = data.lat
        locationValue.longitude = data.long
        val distance = location.distanceTo(locationValue).toDouble()


        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .height(116.dp)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(16.dp)),
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp)) {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .background(Color.White),
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(81.dp), contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = data.imgLogo.ifEmpty { R.drawable.ic_launcher_background },
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(12.dp))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = data.name ?: "",
                        fontSize = 17.sp,
                        color = Purple500,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    //val listSpecialized = data.listSpecializedSector as List<*>

                    Text("Horario: ${data.TimeStart.toString()} a ${data.TimeEnd.toString()} "/*buildString {
                        for (index in listSpecialized.indices) {
                            append(listSpecialized[index])
                            when (index) {
                                listSpecialized.size - 2 -> append(" y ")
                                listSpecialized.size - 1 -> continue
                                else -> append(", ")
                            }
                        }
                    }*/, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))

                    Text(
                        text = if (distance < 1000) "${distance.toInt()}m" else String.format(
                            "%.1f km", distance / 1000
                        ), fontSize = 12.sp
                    )
                }

            }

        }
    }
}