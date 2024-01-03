package com.example.petsall.ui.bussiness.business

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.presentation.bussiness.business.PABusinessListEvent
import com.example.petsall.presentation.bussiness.business.PABusinessListViewModel
import com.example.petsall.presentation.consultingroom.vet.PAVetEvent
import com.example.petsall.ui.components.*
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.encodeJson
import com.example.petsall.utils.isEqual
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PABusinessList(
    navController: NavController,
    viewModel: PABusinessListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val items: MutableList<String> = state.categorySelected.value.toMutableList()
    val itemsSector: MutableList<String> = state.categorySelectedTemp.value.toMutableList()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var clicked by remember { mutableStateOf(false) }
    val enableButton = isEqual(
        state.categorySelectedTemp.value,
        state.categorySelected.value
    )
    if (clicked) {
        clicked = false
    }

    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
                if (location?.isComplete == true) {
                    viewModel.onEvent(PABusinessListEvent.GetDataBusiness(location = location!!))
                }
            }
        }
    }

    LaunchedEffect(state.dataBusiness){
        viewModel.onEvent(PABusinessListEvent.FilterBusiness(state.categorySelected.value))
    }

    Scaffold(content = {
        ModalBottomSheetLayout(sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.padding(0.dp),
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .wrapContentWidth(unbounded = false)
                        .wrapContentHeight(unbounded = true)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        HeaderBottomSheet()
                        Filter(list = state.listCategorys,
                            title = "Categorias",
                            selectedOption = state.categorySelectedTemp.value,
                            onClickOption = { sector ->
                                if (!itemsSector.contains(sector)) itemsSector.add(sector) else itemsSector.remove(
                                    sector
                                )
                                state.categorySelectedTemp.value = itemsSector
                            })
                        ButtonDefault(
                            textButton = "Aplicar",
                            enabled = !enableButton,
                            radius = 8.dp, modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            state.categorySelected.value = state.categorySelectedTemp.value

                            viewModel.onEvent(PABusinessListEvent.FilterBusiness(state.categorySelected.value))

                            coroutine.launch {
                                sheetState.hide()
                            }
                        }
                    }
                }
            }) {
            Column(Modifier.fillMaxSize()) {
                FilterVet(list = state.listCategorys.take(3),
                    title = "Acceso rápido por categoria",
                    counFilters = state.categorySelected.value.count(),
                    selectedSectors = state.categorySelected.value,
                    onClickSector = { sector ->
                        if (!items.contains(sector)) items.add(sector) else items.remove(
                            sector
                        )
                        state.categorySelected.value = items
                        viewModel.onEvent(PABusinessListEvent.FilterBusiness(categoryselected = state.categorySelected.value))
                    }) {
                    coroutine.launch {
                        if (state.categorySelected.value != state.categorySelectedTemp.value) {
                            state.categorySelectedTemp.value = state.categorySelected.value
                        }
                        sheetState.show()
                    }
                }
                if (state.dataBusiness.isNotEmpty()) {
                    LazyColumn {
                        items(state.dataBusinessFilterNew) {
                            location?.let { it1 ->
                                CardBusinessList(it, it1) {
                                    coroutine.launch {
                                        if (!clicked) {
                                            navController.navigate("${Route.PABussinessDetail}/${it.encodeJson()}")
                                            clicked = !clicked
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    })
}


@Preview
@Composable
fun CardBusinessList(
    data: BusinessData = BusinessData(),
    location: Location = Location(""),
    onClick: () -> Unit = {}
) {
    val locationValue = Location("location value.")
    locationValue.latitude = data.locationGeoPoint?.latitude!!
    locationValue.longitude = data.locationGeoPoint.longitude
    val distance = location.distanceTo(locationValue).toDouble()
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(9.dp))
            .clickable {
                onClick.invoke()
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(9.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = if (data.imgBanner?.isNotEmpty() == true) data.imgBanner else R.drawable.alimento,
                contentDescription = "ImgBusiness",
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .width(100.dp), contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = data.name ?: "",
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        color = GreenLight,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "horario: ${data.hStart} - ${data.hEnd}",
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        color = GreenLight,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = if (distance < 1000) "${distance.toInt()}m" else String.format(
                        "%.2f km", distance / 1000
                    ),
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = GreenLight,
                    fontSize = 12.sp, textAlign = TextAlign.End
                )
            }
        }
    }
}