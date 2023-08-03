package com.example.petsall.ui.vet

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.presentation.vet.PAVetEvent
import com.example.petsall.presentation.vet.PAVetViewModel
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.Purple500
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun PAVet(
    navController: NavController,
    viewModel: PAVetViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val state = viewModel.state
    var location by remember { mutableStateOf<Location?>(null) }


    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
                viewModel.onEvent(PAVetEvent.GetDataUser(location = location))
            }
        }
    }


    if (checkLocationPermission(context)) {
        if (state.data.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffF9F9F9))
            ) {
                LazyColumn {
                    items(state.data) { item ->
                        location?.let {
                            CardVet(data = item?.data, id = item?.id.toString(), location = it) {
                                navController.navigate("${Route.PAVetDetail}/${item?.id.toString()}")
                            }
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffF9F9F9))
        ) {
            Text("Debes aceptar los permisos de ubicacion")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardVet(
    location: Location = Location(""),
    data: Map<String, Any>? = mapOf(),
    id: String = "",
    onClick: () -> Unit = {}
) {
    if (!data.isNullOrEmpty()) {
        val locationValue = Location("location value.")
        locationValue.latitude = data["Latitud"] as Double
        locationValue.longitude = data["Longitud"] as Double
        val distancia = location.distanceTo(locationValue).toString()

        distancia.count()
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .height(116.dp)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(16.dp)),
            elevation = 4.dp) {
            Row(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(81.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Image",
                        modifier = Modifier
                            .fillMaxSize().clip(shape = RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }
                Column(modifier = Modifier.fillMaxHeight().padding(start = 8.dp)) {
                    Text(
                        text = data["Nombre"].toString(),
                        fontSize = 17.sp,
                        color = Purple500,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val listSpecialized = data["specialized_sector"] as List<*>

                    Text(buildString {
                        for (index in listSpecialized.indices) {
                            append(listSpecialized[index])
                            when (index) {
                                listSpecialized.size - 2 -> append(" y ")
                                listSpecialized.size - 1 -> continue
                                else -> append(", ")
                            }
                        }
                    }, fontSize = 12.sp,modifier = Modifier.padding(bottom = 4.dp))

                    Text(text = "${distancia.split(".")[0]}m", fontSize = 12.sp)
                }

            }

        }
    }

}