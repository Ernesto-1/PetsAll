package com.example.petsall.ui.components

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun MyMap(modifier: Modifier = Modifier, positionLtLn: LatLng, location: Location, nameBussines:String = "") {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(positionLtLn, 15f)
    }
    val myLocation =
        LatLng(
            location.latitude,
            location.longitude
        )
    val totalLocation = myLocation.let {
        LatLngBounds.Builder()
            .include(positionLtLn)
            .include(it)
            .build()
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false
            )
        )
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
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
                state = MarkerState(position = positionLtLn),
                title = nameBussines
            )
            MapProperties(isMyLocationEnabled = true, isTrafficEnabled = true)
        }
    }

}