package com.example.petsall.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petsall.MainActivity
import com.example.petsall.R
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeViewModel
import com.example.petsall.ui.changepet.CardChangePet
import com.example.petsall.ui.components.PACard
import com.example.petsall.ui.components.PACard2
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.navigation.Route
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.convertTimestampToString
import com.example.petsall.utils.convertTimestampToString2
import com.example.petsall.utils.permissions
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAHome(
    navController: NavController,
    activity: MainActivity,
    viewModel: PAHomeViewModel = hiltViewModel(),
    requestMultiplePermissions: ActivityResultLauncher<Array<String>>
) {
    val state = viewModel.state
    val context = LocalContext.current
    val user = Firebase.auth.currentUser
    var selectPet by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val sharedPreferences = context.getSharedPreferences("nombre_pref", Context.MODE_PRIVATE)
    val valor = sharedPreferences.getString("idPet", "")


    LaunchedEffect(Unit) {
        if (activity.permissionRequestCount < 1) {
            if (!checkLocationPermission(context)) {
                requestMultiplePermissions.launch(permissions)
            }
            activity.incrementPermissionRequestCount()
        }
        if (valor != null) {
            selectPet = valor
        }
        sheetState.hide()
        viewModel.onEvent(PAHomeEvent.GetDataPets(selectPet))
        viewModel.onEvent(PAHomeEvent.GetDatePet(selectPet))
    }

    if (state.data?.isNotEmpty() == true && state.dataPets.isNotEmpty()) {
        val name by rememberSaveable { mutableStateOf(state.data?.get("Nombre")) }
        state.dataPet?.data?.get("Fecha_Nacimiento")?.let { Log.d("FechaNacimientoPet", it.toString() ) }
        LaunchedEffect(selectPet) {
            viewModel.onEvent(PAHomeEvent.GetDataPets(selectPet))
            viewModel.onEvent(PAHomeEvent.GetDatePet(selectPet))
        }

        BackHandler {
            activity.moveTaskToBack(true)
            //activity.finish()
        }

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.Center) {
                        Row {
                            if (state.dataPet?.data?.get("ImgUrl").toString() != "") {
                                AsyncImage(
                                    model = state.dataPet?.data?.get("ImgUrl").toString(),
                                    contentDescription = "Translated description of what the image contains",
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp)
                                        .clip(shape = RoundedCornerShape(50.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.fish),
                                    contentDescription = "Image pet local",
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp)
                                        .clip(shape = RoundedCornerShape(50.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = state.dataPet?.data?.get("Nombre").toString(),
                                modifier = Modifier.align(
                                    Alignment.CenterVertically
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "",
                                modifier = Modifier
                                    .align(
                                        Alignment.CenterVertically
                                    )
                                    .clickable {
                                        scope.launch {
                                            sheetState.show()
                                        }
                                    }
                            )
                        }

                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
            )
        }, drawerElevation = 0.dp, drawerShape = MaterialTheme.shapes.small, content = {
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
                                    .background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (state.dataPets.isNotEmpty()) {
                                    LazyColumn {
                                        items(state.dataPets) { item ->
                                            CardChangePet(data = item?.data,
                                                "",
                                                isSelected = selectPet == item?.id.toString(),
                                                onClick = {
                                                    scope.launch {
                                                        selectPet = item?.id.toString()
                                                        sharedPreferences.edit()
                                                            .putString("idPet", selectPet).apply()
                                                        sheetState.hide()
                                                    }
                                                })
                                        }
                                        item {
                                            if (state.numPets) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable(onClick = {
                                                            navController.navigate(Route.PANewPet)
                                                        })
                                                        .wrapContentHeight(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .weight(2f)
                                                            .padding(
                                                                vertical = 10.dp, horizontal = 15.dp
                                                            ),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Add,
                                                            contentDescription = "ImageLocal",
                                                            modifier = Modifier
                                                                .height(70.dp)
                                                                .width(70.dp)
                                                                .clip(
                                                                    shape = RoundedCornerShape(
                                                                        50.dp
                                                                    )
                                                                )
                                                        )
                                                        Text(
                                                            text = "Agregar mascota",
                                                            fontSize = 20.sp,
                                                            modifier = Modifier.padding(horizontal = 8.dp),
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hola ${name}!",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 35.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(50.dp))

                    if (state.datePet.isNotEmpty()) {
                        val datePet = convertTimestampToString2(state.datePet[0]?.data?.get("day") as Timestamp)
                        if (state.datePet[0]?.data?.get("status").toString() == "pendiente") {
                            Text(
                                text = "Tienes una cita pendiente de ser confirmada por la clinica",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 77.dp),
                                textAlign = TextAlign.Center,
                                color = Color(0xff84B1B8)
                            )
                        } else if (state.datePet[0]?.data?.get("status")
                                .toString() == "confirmado"
                        ) {
                            Text(
                                text = "${
                                    state.dataPet?.data?.get("Nombre").toString()
                                } tiene una cita el $datePet ",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 77.dp),
                                textAlign = TextAlign.Center,
                                color = Color(0xff84B1B8)
                            )
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }

                    Row {
                        PACard2(
                            iconCard = R.drawable.healt,
                            txtCard = "Cartilla de vacunacion",
                            colorIcon = Color(0xFF99F1A7),
                            modifier = Modifier.weight(1f)
                        ){
                            navController.navigate("${Route.PAVaccinationCard}/${user?.uid.toString()}/${state.dataPet?.id}")
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        PACard2(
                            iconCard = R.drawable.proceedings,
                            txtCard = "Expediente",
                            colorIcon = Color(0xff78CEFF),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    PACard(
                        iconCard = R.drawable.request,
                        txtCard = "Solicitar constancia digital",
                        colorIcon = Color(0xffF0E1FF)
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                }
            }
        })
    }
}
