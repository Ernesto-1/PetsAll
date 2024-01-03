package com.example.petsall.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petsall.R
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.utils.AppConstans

@Preview
@Composable
fun CardDateOfPet(
    data: PetData = PetData(),
    id: String = "",
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent
    if (data.id?.isNotEmpty() == true) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =  "Seleccionado",
                color = if (isSelected) borderColor else Color.Transparent,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .border(1.dp, borderColor, shape = RoundedCornerShape(50.dp)),
                elevation = if (isSelected) 4.dp else 1.dp, shape = RoundedCornerShape(50.dp)
            ) {
                Column(
                    modifier = Modifier.border(
                        1.dp,
                        Color(0xffeaeaea),
                        shape = CircleShape
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = if (data.img?.isNotEmpty() == true) data.img else when (data.pet) {
                            AppConstans.SpeciesConstants.FISH -> R.drawable.fish
                            AppConstans.SpeciesConstants.DOG -> R.drawable.icon_dog
                            AppConstans.SpeciesConstants.CAT -> R.drawable.cat_face
                            AppConstans.SpeciesConstants.BIRD -> R.drawable.bird
                            else -> R.drawable.icon_dog
                        },
                        contentDescription = "imageFromUrl",
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text =  data.name ?: "",
                color = if (isSelected) borderColor else GreenLight,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}