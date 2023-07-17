package com.example.petsall.ui.vet

import android.location.Location
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vet.PAVetEvent
import com.example.petsall.presentation.vet.PAVetViewModel
import com.example.petsall.ui.navigation.Route
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun PAVet(
    navController: NavController,
    viewModel: PAVetViewModel = hiltViewModel(),
) {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    val state = viewModel.state
    var location by remember { mutableStateOf<Location?>(null) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { locationn: Location? ->
                    location = locationn
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

@Preview
@Composable
fun CardVet(location: Location = Location(""), data: Map<String, Any>? = mapOf(), id: String = "", onClick: () -> Unit = {}) {
    if (data?.isNotEmpty() == true) {
        val locationValue = Location("location value.")
        locationValue.latitude = data["Latitud"] as Double
        locationValue.longitude= data["Longitud"] as Double
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
            Column(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = data["Nombre"].toString(),
                        fontSize = 17.sp,
                        color = Color(0xff82649E)
                    )
                    Text(text = "4.9", fontSize = 10.sp)
                }
                Text(text = "Mascotas: Perros y gatos", fontSize = 12.sp)
                Text(text = "${distancia.split(".")[0]}m", fontSize = 12.sp)
            }

        }
    }

}