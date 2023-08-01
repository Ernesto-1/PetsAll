package com.example.petsall.ui.emergency

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.petsall.presentation.emergency.PAEmergencyEvent
import com.example.petsall.presentation.emergency.PAEmergencyViewModel
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun PAEmergency(viewModel: PAEmergencyViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val state = viewModel.state
    var location by remember { mutableStateOf<Location?>(null) }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false
            )
        )
    }
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
                viewModel.onEvent(PAEmergencyEvent.GetVetEmergency(location))
            }
        }
    }

    if (checkLocationPermission(context)) {
        if (!state.data?.data.isNullOrEmpty() && checkLocationPermission(context)) {

            val businessLocation =
                LatLng(
                    state.data?.data?.get("Latitud") as Double,
                    state.data?.data?.get("Longitud") as Double
                )
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(businessLocation, 15f)
            }
            val myLocation =
                location?.latitude?.let {
                    location?.longitude?.let { it1 ->
                        LatLng(
                            it,
                            it1
                        )
                    }
                }
            val totalLocation = myLocation?.let {
                LatLngBounds.Builder()
                    .include(businessLocation)
                    .include(it)
                    .build()
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .background(Color.White)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(16.dp)),
                    elevation = 5.dp
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = uiSettings,
                        properties = MapProperties(
                            isMyLocationEnabled = true,
                            minZoomPreference = 14.5f,
                            maxZoomPreference = 16.0f,
                            latLngBoundsForCameraTarget = totalLocation
                        )
                    ) {
                        Marker(
                            state = MarkerState(position = businessLocation),
                            title = "Singapore",
                            snippet = "Marker in Singapore",
                        )
                        MapProperties(isMyLocationEnabled = true, isTrafficEnabled = true)
                    }
                }
            }
        }
    }
}