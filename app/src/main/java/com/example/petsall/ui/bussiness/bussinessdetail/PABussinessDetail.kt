package com.example.petsall.ui.bussiness.bussinessdetail

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.ui.components.CarouselOfImages
import com.example.petsall.ui.components.MyMap
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.listSpecialized
import com.example.petsall.ui.theme.BackGroud
import com.example.petsall.ui.theme.Purple200
import com.example.petsall.ui.theme.Snacbar
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

data class CardItem(val id: Int, val imageRes: Int, val nameCard: String)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PABussinessDetail(dataBussiness: BusinessData = BusinessData(), navController: NavController) {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
            }
        }
    }


    Log.d("DataBussiness", dataBussiness.toString())
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = dataBussiness.name.toString(),
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
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val images = listOf(
                "https://cdn.forbes.com.mx/2014/12/mascotas.gif",
                "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
            )
            Text(
                text = dataBussiness.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = BackGroud
            )

            CarouselOfImages(
                itemsCount = images.size,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images[index])
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )

            listSpecialized(
                dataBussiness.articles as List<*>?,
                title = "Articulos"
            )
            Text(
                text = "Ubicacion",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Purple200, modifier = Modifier.padding(vertical = 16.dp)
            )
            if (location?.isComplete == true) {
                location?.let { myLocation ->
                    MyMap(
                        modifier = Modifier
                            .height(400.dp)
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        positionLtLn = LatLng(
                            dataBussiness.latitude,
                            dataBussiness.length
                        ),
                        location = myLocation,
                        nameBussines = dataBussiness.name ?: ""
                    )
                }
            }
        }
    })
}