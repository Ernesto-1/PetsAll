package com.example.petsall.ui.business

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.petsall.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.presentation.business.PABusinessListEvent
import com.example.petsall.presentation.business.PABusinessListViewModel
import com.example.petsall.ui.components.CarouselOfImages
import com.example.petsall.ui.components.MyMap
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.listSpecialized
import com.example.petsall.ui.theme.BackGroud
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.ui.theme.Purple200
import com.example.petsall.ui.theme.Snacbar
import com.example.petsall.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PABusinessList(
    nameListBusiness: String,
    navController: NavController,
    viewModel: PABusinessListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    if (checkLocationPermission(context)) {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
            location = loc
        }
    }

    LaunchedEffect(Unit){
        viewModel.onEvent(PABusinessListEvent.GetDataBusiness(nameListBusiness = nameListBusiness.lowercase()))
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = nameListBusiness,
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

        ModalBottomSheetLayout(sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            sheetContent = {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(unbounded = false)
                        .wrapContentHeight(unbounded = true)

                ) {
                    Column(
                        modifier = Modifier
                            .height(600.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val images = listOf(
                            "https://cdn.forbes.com.mx/2014/12/mascotas.gif",
                            "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
                        )
                        HeaderBottomSheet()
                        if (state.dataBusinesSelected.value.name?.isNotEmpty() == true) {
                            Text(
                                text = state.dataBusinesSelected.value.name ?: "",
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
                                state.dataBusinesSelected.value.articles as List<*>?,
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
                                            state.dataBusinesSelected.value.latitude,
                                            state.dataBusinesSelected.value.length
                                        ),
                                        location = myLocation,
                                        nameBussines = state.dataBusinesSelected.value.name ?: ""
                                    )
                                }
                            }
                        }
                    }

                }


            }) {
            Column(Modifier.fillMaxSize()) {
                if (state.dataBusiness.isNotEmpty()) {
                    LazyColumn {
                        items(state.dataBusiness) {
                            CardBusinessList(it) {
                                coroutine.launch {
                                    state.dataBusinesSelected.value = it
                                    sheetState.show()
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    )

}


@Preview
@Composable
fun CardBusinessList(data: BusinessData = BusinessData(), onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(196.dp)
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(9.dp))
            .clickable {
                onClick.invoke()
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(9.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = if (data.imgBanner?.isNotEmpty() == true) data.imgBanner else R.drawable.alimento,
                contentDescription = "ImgBusiness",
                modifier = Modifier
                    .height(142.dp)
                    .fillMaxWidth(), contentScale = ContentScale.Crop
            )
            Text(
                text = data.name ?: "",
                modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                fontWeight = FontWeight.Bold,
                color = GreenLight,
                fontSize = 16.sp
            )
        }
    }
}