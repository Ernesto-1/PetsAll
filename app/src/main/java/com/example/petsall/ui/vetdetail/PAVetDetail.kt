package com.example.petsall.ui.vetdetail

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.petsall.R
import com.example.petsall.presentation.vetdetail.PAVetDetailEvent
import com.example.petsall.presentation.vetdetail.PAVetDetailViewModel
import com.example.petsall.ui.components.CarouselOfImages
import com.example.petsall.ui.components.CardDateOfPet
import com.example.petsall.ui.components.MyMap
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.listSpecialized
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.*
import com.example.petsall.utils.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAVetDetail(
    vetDetail: String,
    navController: NavController,
    viewModel: PAVetDetailViewModel = hiltViewModel()
) {
    val date = rememberSaveable { mutableStateOf("") }
    val problemDate = listOf("Consulta general", "Vacunacion")
    val expandedState = rememberSaveable { mutableStateOf(false) }
    val expandedStateConsult = rememberSaveable { mutableStateOf(false) }
    var selectedTime by rememberSaveable { mutableStateOf("") }
    var selectedProblem by rememberSaveable { mutableStateOf("") }
    var selectedImage by rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var location by remember { mutableStateOf<Location?>(null) }
    val enableButton = false
    val color = Color.Black
    val focusManager = LocalFocusManager.current
    val mYear: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mCalendar.time = Date()


    val mDatePickerDialog = datePicker(date = date, context = context, focusManager = focusManager)
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    BackHandler {
        navController.navigateUp()
    }

    LaunchedEffect(Unit) {
        if (vetDetail.isNotEmpty()) {
            viewModel.onEvent(PAVetDetailEvent.GetVet(vetDetail))
            viewModel.onEvent(PAVetDetailEvent.GetDataPets(""))
        }

        if (checkLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
            }
        }
    }



    if (!state.dataVet.isNullOrEmpty() && checkLocationPermission(context)) {

        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = state.dataVet!!["Nombre"].toString(),
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
            mDatePickerDialog.datePicker.minDate = mCalendar.timeInMillis
            mCalendar.set(mYear + 1, 11, 31)
            mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis
            ModalBottomSheetLayout(sheetState = sheetState,
                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                modifier = Modifier.padding(0.dp),
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(unbounded = false)
                            .wrapContentHeight(unbounded = true)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            HeaderBottomSheet()
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

                                Text(
                                    text = "Selecciona al paciente",
                                    modifier = Modifier.padding(vertical = 5.dp)
                                )
                                LazyRow(
                                    contentPadding = PaddingValues(
                                        horizontal = 40.dp, vertical = 8.dp
                                    ), horizontalArrangement = Arrangement.spacedBy(15.dp)
                                ) {
                                    items(state.dataPets) { item ->
                                        CardDateOfPet(data = item?.data,
                                            "",
                                            isSelected = selectedImage == item?.id.toString(),
                                            onClick = { selectedImage = item?.id.toString() })
                                    }

                                }

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
                                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                                            alpha = 0.15f
                                        ),
                                        textColor = color,
                                        focusedLabelColor = color
                                    ),
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            mDatePickerDialog.show()
                                        }) {
                                            Icon(
                                                Icons.Filled.DateRange,
                                                contentDescription = "fecha"
                                            )
                                        }
                                    },
                                    readOnly = true,
                                    enabled = false
                                )

                                OutlinedTextField(
                                    value = selectedProblem,
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
                                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                                            alpha = 0.15f
                                        ),
                                        textColor = color,
                                        focusedLabelColor = color
                                    ),
                                    textStyle = MaterialTheme.typography.body1,
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            expandedStateConsult.value = true
                                        }) {
                                            Icon(
                                                Icons.Filled.ArrowDropDown,
                                                contentDescription = "Expandir opciones"
                                            )
                                        }
                                    },
                                    readOnly = true,
                                    enabled = false
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
                                if (date.value.isNotEmpty()) {
                                    val availableTimes = generateAvailableTimes(
                                        dateString = date.value,
                                        state.dataVet?.get("HInicio").toString(),
                                        state.dataVet?.get("HFin").toString()
                                    )
                                    OutlinedTextField(
                                        value = selectedTime,
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
                                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                                                alpha = 0.15f
                                            ),
                                            textColor = color,
                                            focusedLabelColor = color
                                        ),
                                        textStyle = MaterialTheme.typography.body1,
                                        trailingIcon = {
                                            IconButton(onClick = {
                                                if (availableTimes.isNotEmpty()) {
                                                    expandedState.value = true
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "No hay citas disponibles intenta con otro dia",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }) {
                                                Icon(
                                                    Icons.Filled.ArrowDropDown,
                                                    contentDescription = "Expandir opciones"
                                                )
                                            }
                                        },
                                        readOnly = true,
                                        enabled = false
                                    )
                                    DropdownMenu(expanded = expandedState.value,
                                        onDismissRequest = { expandedState.value = false }) {
                                        if (availableTimes.isNotEmpty()) {
                                            availableTimes.forEach { times ->
                                                DropdownMenuItem(onClick = {
                                                    selectedTime = times
                                                    expandedState.value = false
                                                }) {
                                                    Text(text = times)
                                                }
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "No hay citas disponibles intenta con otro dia",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                    }
                                }

                            }
                            Card(
                                elevation = 1.dp,
                                modifier = Modifier
                                    .height(88.dp)
                                    .fillMaxWidth()
                                    .shadow(10.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    ButtonDefault(modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .height(48.dp)
                                        .width(216.dp),
                                        enabled = !enableButton, radius = 16.dp,
                                        textButton = "Solicitar cita",
                                        onClick = {
                                            if (date.value.isNotEmpty() && selectedTime.isNotEmpty() && selectedProblem.isNotEmpty()) {
                                                val timestamp = convertDateTimeToTimestamp(
                                                    dateString = date.value,
                                                    timeString = selectedTime.split(" ")[0]
                                                )
                                                viewModel.onEvent(
                                                    PAVetDetailEvent.RegisterDate(
                                                        day = timestamp,
                                                        time = selectedTime,
                                                        patient = selectedImage,
                                                        reason = selectedProblem,
                                                        idVet = vetDetail
                                                    )
                                                )
                                                coroutine.launch { sheetState.hide() }
                                            }
                                        })
                                }
                            }
                        }
                    }
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Image",
                            modifier = Modifier
                                .height(110.dp)
                                .width(110.dp)
                                .clip(shape = RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.FillWidth
                        )

                        ButtonDefault(
                            textButton = "Solicitar cita",
                            modifier = Modifier.padding(top = 25.dp, bottom = 18.dp), radius = 12.dp
                        ) {
                            coroutine.launch {
                                sheetState.show()
                            }
                        }
                    }
                    val images = listOf(
                        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
                    )
                    Text(
                        text = "Consultorio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Purple200
                    )

                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        CarouselOfImages(
                            itemsCount = images.size,
                            itemContent = { index ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(images[index])
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.height(200.dp)
                                )
                            }
                        )
                    }

                    listSpecialized(state.dataVet?.get("medical_specialties") as List<*>?)

                    Text(
                        text = "Ubicacion",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Purple200, modifier = Modifier.padding(vertical = 16.dp)
                    )
                    if (location?.isComplete == true ){
                        location?.let { it1 ->
                            MyMap(modifier = Modifier.height(220.dp), positionLtLn = LatLng(
                                state.dataVet?.get("Latitud") as Double, state.dataVet?.get("Longitud") as Double
                            ), location = it1, nameBussines = state.dataVet!!["Nombre"].toString()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        })
    }
}