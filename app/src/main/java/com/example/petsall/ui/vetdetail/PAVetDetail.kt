package com.example.petsall.ui.vetdetail

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vetdetail.PAVetDetailEvent
import com.example.petsall.presentation.vetdetail.PAVetDetailViewModel
import com.example.petsall.ui.components.CardDateOfPet
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.Black
import com.example.petsall.ui.theme.BtnBlue
import com.example.petsall.ui.theme.plata
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.datePicker
import com.example.petsall.utils.generateAvailableTimes
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAVetDetail(
    vetDetail: String,
    navController: NavController,
    viewModel: PAVetDetailViewModel = hiltViewModel()
) {
    val date = rememberSaveable { mutableStateOf("") }
    val problemDate = listOf("Consulta general","Vacunacion")
    val expandedState = rememberSaveable { mutableStateOf(false) }
    val expandedStateConsult = rememberSaveable { mutableStateOf(false) }
    var selectedTime by rememberSaveable { mutableStateOf("") }
    var selectedProblem by rememberSaveable { mutableStateOf("") }
    var selectedImage by rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false
            )
        )
    }
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var location by remember { mutableStateOf<Location?>(null) }
    val enableButton = false
    val color = Color(Black.value)
    val focusManager = LocalFocusManager.current
    val mYear: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mCalendar.time = Date()

    val mDatePickerDialog = datePicker(date = date, context = context,focusManager = focusManager)
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    BackHandler {
        navController.navigateUp()
    }

    LaunchedEffect(Unit) {
        if (vetDetail.isNotEmpty()){
            viewModel.onEvent(PAVetDetailEvent.GetVet(vetDetail))
        }

        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
            }
        }
    }



    if (!state.dataVet.isNullOrEmpty() && checkLocationPermission(context)) {
        val businessLocation =
            LatLng(
                state.dataVet?.get("Latitud") as Double,
                state.dataVet?.get("Longitud") as Double
            )
        val myLocation =
            location?.latitude?.let {
                location?.longitude?.let { it1 ->
                    LatLng(
                        it,
                        it1
                    )
                }
            }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(businessLocation, 15f)
        }
        val totalLocation = myLocation?.let {
            LatLngBounds.Builder()
                .include(businessLocation)
                .include(it)
                .build()
        }

        val availableTimes = generateAvailableTimes(state.dataVet?.get("HInicio").toString(),state.dataVet?.get("HFin").toString())

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = state.dataVet!!["Nombre"].toString(),
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            textAlign = TextAlign.End
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    }, backgroundColor = Color(
                        0xff84B1B8
                    )
                )
            }, content = {
                mDatePickerDialog.datePicker.minDate = mCalendar.timeInMillis
                mCalendar.set(mYear + 1, 11 , 31)
                mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis
                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.padding(0.dp),
                    sheetContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BtnBlue)
                                .wrapContentWidth(unbounded = false)
                                .wrapContentHeight()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                HeaderBottomSheet(
                                    "Cita",
                                    Icons.Filled.Close
                                ) {
                                    coroutine.launch { sheetState.hide() }
                                    date.value = ""
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(327.dp)
                                        .background(Color.White)
                                        .verticalScroll(rememberScrollState()),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .background(Color.White)
                                    )
                                    OutlinedTextField(
                                        value = date.value,
                                        onValueChange = { date.value = it },
                                        label = { Text("Fecha de la cita") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .width(282.dp)
                                            .padding(vertical = 5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = plata,
                                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                                            textColor = color,
                                            focusedLabelColor = color
                                        ),
                                        trailingIcon = {
                                            IconButton(onClick = {
                                                mDatePickerDialog.show()
                                            }) {
                                                Icon(
                                                    Icons.Filled.DateRange, contentDescription = "fecha"
                                                )
                                            }
                                        },
                                        readOnly = true, enabled = false
                                    )

                                    OutlinedTextField(value = selectedTime,
                                        onValueChange = { selectedTime = it },
                                        label = { Text("Selecciona un horario") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .width(282.dp)
                                            .padding(vertical = 5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = plata,
                                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                                            textColor = color,
                                            focusedLabelColor = color
                                        ),
                                        textStyle = MaterialTheme.typography.body1,
                                        trailingIcon = {
                                            IconButton(onClick = { expandedState.value = true }) {
                                                Icon(
                                                    Icons.Filled.ArrowDropDown,
                                                    contentDescription = "Expandir opciones"
                                                )
                                            }
                                        },
                                        readOnly = true,enabled = false
                                    )
                                    DropdownMenu(expanded = expandedState.value,
                                        onDismissRequest = { expandedState.value = false }) {
                                        availableTimes.forEach { times ->
                                            DropdownMenuItem(onClick = {
                                                selectedTime = times
                                                expandedState.value = false
                                            }) {
                                                Text(text = times)
                                            }
                                        }
                                    }

                                    Text(text = "Selecciona al paciente", modifier = Modifier.padding(vertical = 5.dp))
                                    LazyRow(
                                        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                                    ) {
                                        items(state.dataPets) { item ->
                                            CardDateOfPet(data = item?.data, "", isSelected = selectedImage == item?.id.toString()
                                                ,onClick = { selectedImage =  item?.id.toString()})
                                        }

                                    }

                                    OutlinedTextField(value = selectedProblem,
                                        onValueChange = { selectedProblem = it },
                                        label = { Text("Selecciona el motivo") },
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .width(282.dp)
                                            .padding(vertical = 5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = plata,
                                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                                            textColor = color,
                                            focusedLabelColor = color
                                        ),
                                        textStyle = MaterialTheme.typography.body1,
                                        trailingIcon = {
                                            IconButton(onClick = { expandedStateConsult.value = true }) {
                                                Icon(
                                                    Icons.Filled.ArrowDropDown,
                                                    contentDescription = "Expandir opciones"
                                                )
                                            }
                                        },
                                        readOnly = true,enabled = false
                                    )
                                    DropdownMenu(expanded = expandedStateConsult.value,
                                        onDismissRequest = { expandedStateConsult.value = false }) {
                                        problemDate.forEach { problem ->
                                            DropdownMenuItem(onClick = {
                                                selectedProblem = problem
                                                expandedStateConsult.value = false
                                            }) {
                                                Text(text = problem)
                                            }
                                        }
                                    }

                                }
                                Card(
                                    elevation = 1.dp, modifier = Modifier
                                        .height(88.dp)
                                        .fillMaxWidth()
                                        .shadow(10.dp)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        ButtonDefault(
                                            modifier = Modifier
                                                .align(alignment = Alignment.Center)
                                                .height(48.dp)
                                                .width(216.dp),
                                            enabled = !enableButton,
                                            textButton = "Solicitar cita",
                                            onClick = {
                                                viewModel.onEvent(PAVetDetailEvent.RegisterDate(day = date.value, time = selectedTime, patient = selectedImage, reason = selectedProblem, idVet = vetDetail))
                                                coroutine.launch { sheetState.hide() }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
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
                                )) {
                                Marker(
                                    state = MarkerState(position = businessLocation),
                                    title = "Singapore",
                                    snippet = "Marker in Singapore",
                                )
                                MapProperties(isMyLocationEnabled = true, isTrafficEnabled = true)
                            }
                        }
                        Button(onClick = {
                            coroutine.launch {
                                sheetState.show()
                                viewModel.onEvent(PAVetDetailEvent.GetDataPets(""))
                            }
                        }) {

                        }
                    }
                }

            })

    }
}


