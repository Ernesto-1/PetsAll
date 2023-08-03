package com.example.petsall.ui.emergency

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.petsall.presentation.emergency.PAEmergencyEvent
import com.example.petsall.presentation.emergency.PAEmergencyViewModel
import com.example.petsall.ui.components.MyMap
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.RedAlert
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PAEmergency(viewModel: PAEmergencyViewModel = hiltViewModel()) {
    val state = viewModel.state
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenWidth * 0.70f)
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
                viewModel.onEvent(PAEmergencyEvent.GetVetEmergency(location))
            }
        }
    }
    if (location?.isComplete == true && !state.data?.data.isNullOrEmpty()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            location?.let {
                MyMap(
                    modifier = Modifier.height(imageHeight), positionLtLn = LatLng(
                        state.data?.data?.get("Latitud") as Double,
                        state.data?.data?.get("Longitud") as Double
                    ), location = it, nameBussines = state.data?.data?.get("Nombre") as String
                )
            }

            ButtonDefault(
                textButton = "Enviar alerta",
                modifier = Modifier.padding(top = 25.dp, bottom = 18.dp), radius = 12.dp, colorBackground = RedAlert
            ) {

            }
        }
    }
}
