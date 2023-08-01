package com.example.petsall.ui.vaccination

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.petsall.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vaccination.PAVaccinationEvent
import com.example.petsall.presentation.vaccination.PAVaccinationViewModel
import com.example.petsall.ui.theme.*
import com.example.petsall.utils.convertTimestampToString
import com.google.firebase.Timestamp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAVaccination(
    idUser: String,
    idPet: String,
    navController: NavController,
    viewModel: PAVaccinationViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.onEvent(PAVaccinationEvent.GetVaccinationList(idUser = idUser, idPet = idPet))
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text ="Cartilla",
                color = Snacbar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                textAlign = TextAlign.End
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Snacbar
                )
            }
        }, backgroundColor = Color.White, elevation = 0.dp
        )
    }, content = {
        if (state.data.isNotEmpty()) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)) {
                items(state.data) {
                    CardVaccine(data = it?.data)
                }

            }
        }
    })


}


@Preview(showBackground = true)
@Composable
fun CardVaccine(modifier: Modifier = Modifier, data: Map<String, Any>? = mapOf()) {
    val colorStatus: Color = when (data?.get("status")?.toString()) {
        "pendiente" -> statusEarring
        "vencido" -> statusDefeated
        "vigente" -> statusCurrent
        else -> statusEarring
    }
    val date = convertTimestampToString(data?.get("date") as Timestamp)
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
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
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxHeight()
                    .width(81.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (data["type"]?.toString() == "vacuna") painterResource(id = R.drawable.vaccinee) else painterResource(id = R.drawable.medicines),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    val newStatusName = data["status"]?.toString() ?: ""
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
                            text = newStatusName,
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
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = data["vaccine"]?.toString() ?: "",
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
                        text = "Ced. Prof." + data["PLicense"]?.toString(),
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

