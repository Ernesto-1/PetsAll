package com.example.petsall.ui.vet

import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vet.PAVetEvent
import com.example.petsall.presentation.vet.PAVetViewModel
import com.example.petsall.ui.components.*
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.*
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.encodeJson
import com.example.petsall.utils.isEqual
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
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
    val items: MutableList<String> = state.selectedSector.value.toMutableList()
    val itemsSector: MutableList<String> = state.selectedSectorTemp.value.toMutableList()
    val itemsSpecialties: MutableList<String> =
        state.selectedSpecialtiesTemp.value.toMutableList()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()
    val enableButton = isEqual(
        state.selectedSectorTemp.value,
        state.selectedSector.value
    ) && isEqual(state.selectedSpecialtiesTemp.value, state.selectedSpecialties.value)
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
    }

    LaunchedEffect(Unit) {
        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
                viewModel.onEvent(PAVetEvent.GetDataVet(location = location))
            }
        }
    }

    if (checkLocationPermission(context)) {
        if (state.dataVet.isNotEmpty()) {

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
                            Filter(list = state.listSector,
                                title = "Filtrar por mascota",
                                selectedOption = state.selectedSectorTemp.value,
                                onClickOption = { sector ->
                                    if (!itemsSector.contains(sector)) itemsSector.add(sector) else itemsSector.remove(
                                        sector
                                    )
                                    state.selectedSectorTemp.value = itemsSector
                                })
                            Filter(list = state.listSpecialties,
                                title = "Filtrar por especialista",
                                selectedOption = state.selectedSpecialtiesTemp.value,
                                onClickOption = { specialties ->
                                    if (!itemsSpecialties.contains(specialties)) itemsSpecialties.add(
                                        specialties
                                    ) else itemsSpecialties.remove(
                                        specialties
                                    )
                                    state.selectedSpecialtiesTemp.value = itemsSpecialties
                                })
                            ButtonDefault(
                                textButton = "Aplicar",
                                enabled = !enableButton,
                                radius = 8.dp, modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                state.selectedSector.value = state.selectedSectorTemp.value
                                state.selectedSpecialties.value =
                                    state.selectedSpecialtiesTemp.value

                                state.dataVetFilter = state.dataVet.filter { vetData ->
                                    val sectoresEspecialidades = (vetData.listSpecializedSector
                                        ?: emptyList()) + (vetData.listSpecialties ?: emptyList())
                                    sectoresEspecialidades.any { it in state.selectedSector.value || it in state.selectedSpecialties.value }
                                }
                                coroutine.launch {
                                    sheetState.hide()
                                }
                            }
                        }
                    }
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffF9F9F9))
                ) {
                    FilterVet(list = state.listSector.take(3),
                        title = "Acceso rápido por mascotas",
                        counFilters = state.selectedSector.value.count() + state.selectedSpecialties.value.count(),
                        selectedSectors = state.selectedSector.value,
                        onClickSector = { sector ->
                            if (!items.contains(sector)) items.add(sector) else items.remove(
                                sector
                            )
                            state.selectedSector.value = items
                            state.dataVetFilter = state.dataVet.filter { vetData ->
                                val sectoresEspecialidades = (vetData.listSpecializedSector
                                    ?: emptyList()) + (vetData.listSpecialties ?: emptyList())
                                sectoresEspecialidades.any { it in state.selectedSector.value || it in state.selectedSpecialties.value }
                            }

                        }) {
                        coroutine.launch {
                            if (state.selectedSector.value != state.selectedSectorTemp.value) {
                                state.selectedSectorTemp.value = state.selectedSector.value
                            }
                            if (state.selectedSpecialties.value != state.selectedSpecialtiesTemp.value) {
                                state.selectedSpecialtiesTemp.value =
                                    state.selectedSpecialties.value
                            }
                            sheetState.show()
                        }
                    }
                    LazyColumn {
                        items(if (state.selectedSector.value.isEmpty() && state.selectedSpecialties.value.isEmpty()) state.dataVet else state.dataVetFilter) { item ->
                            location?.let {
                                CardVet(data = item, id = item.id, location = it) {
                                    if (!clicked) {
                                        navController.navigate("${Route.PAVetDetail}/${item.encodeJson()}")
                                        clicked = !clicked
                                    }

                                }
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