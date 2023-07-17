package com.example.petsall.ui.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.petsall.MainActivity
import com.example.petsall.R
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeViewModel
import com.example.petsall.ui.components.PACard
import com.example.petsall.ui.navigation.Route
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.permissions

@SuppressLint("RememberReturnType")
@Composable
fun PAHome(
    navController: NavController,
    activity: MainActivity,
    viewModel: PAHomeViewModel = hiltViewModel(),
    requestMultiplePermissions: ActivityResultLauncher<Array<String>>
) {
    val state = viewModel.state
    val context = LocalContext.current
    val idPet =
        navController.currentBackStackEntryAsState().value?.savedStateHandle?.get<String>("idPet")
    var selectPet by rememberSaveable { mutableStateOf("") }
    val sharedPreferences = context.getSharedPreferences("nombre_pref", Context.MODE_PRIVATE)
    val valor = sharedPreferences.getString("idPet", "")

    LaunchedEffect(Unit) {
        if (activity.permissionRequestCount < 1) {
            if (!checkLocationPermission(context)) {
                requestMultiplePermissions.launch(permissions)
            }
            activity.incrementPermissionRequestCount()
        }
    }

    if (idPet?.isNotEmpty() == true) {
        selectPet = idPet
        sharedPreferences.edit().putString("idPet", selectPet).apply()
    } else {
        if (valor != null) {
            selectPet = valor
        }
    }

    if (state.data?.isNotEmpty() == true) {
        val name by rememberSaveable { mutableStateOf(state.data?.get("Nombre")) }

        LaunchedEffect(selectPet) {
            viewModel.onEvent(PAHomeEvent.GetDataPets(selectPet))
            viewModel.onEvent(PAHomeEvent.GetDatePet(selectPet))
        }

        BackHandler {
            activity.moveTaskToBack(true)
            //activity.finish()
        }

        Column(
            modifier = Modifier
                .background(Color(0xffF9F9F9))
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

            if (state.dataPets.isNotEmpty()) {
                Text(
                    text = "Escoge a tu mascota para conocer su proxima cita",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 40.dp),
                    textAlign = TextAlign.Center
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.dataPets[0]?.data?.get("Nombre").toString(),
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp), fontSize = 15.sp
                    )
                    if (state.dataPets[0]?.data?.get("ImgUrl").toString() != "") {
                        AsyncImage(
                            model = state.dataPets[0]?.data?.get("ImgUrl").toString(),
                            contentDescription = "Translated description of what the image contains",
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                                .clip(shape = RoundedCornerShape(50.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.fish),
                            contentDescription = "Image pet local",
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                                .clip(shape = RoundedCornerShape(50.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }


                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    textDecoration = TextDecoration.None,
                                    fontSize = 12.sp,
                                )
                            ) {
                                append("Cambiar mascota")
                            }
                        },
                        onClick = {
                            navController.navigate("${Route.PAChangePet}/${state.dataPets[0]?.id.toString()}")
                        }
                    )
                }
            } else {
                Text(
                    text = "No tienes mascotas registradas",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 77.dp),
                    textAlign = TextAlign.Center, color = Color(0xff84B1B8)
                )
                Spacer(modifier = Modifier.height(33.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.None,
                                color = Color(0xffDEC7F5),
                                fontSize = 14.sp,
                            )
                        ) {
                            append("Registrar a mi mascota")
                        }
                    },
                    onClick = {
                        navController.navigate(Route.PANewPet)
                    }
                )
            }
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.None,
                            color = Color(0xffDEC7F5),
                            fontSize = 14.sp,
                        )
                    ) {
                        append("Agregar nueva mascota")
                    }
                },
                onClick = {
                    navController.navigate(Route.PANewPet)
                }, modifier = Modifier.padding(top = 24.dp, bottom = 5.dp)
            )

            if (state.datePet.isNotEmpty()){
                if (state.datePet[0]?.data?.get("status").toString() == "earring"){
                    Text(
                        text = "Tienes una cita pendiente de ser confirmada por la clinica",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 77.dp),
                        textAlign = TextAlign.Center, color = Color(0xff84B1B8)
                    )
                }else if (state.datePet[0]?.data?.get("status").toString() == "confirmed"){
                    Text(
                        text = "${state.dataPets[0]?.data?.get("Nombre").toString()} tiene una cita el ${state.datePet[0]?.data?.get("day").toString()} en horario de ${
                            state.datePet[0]?.data?.get("time")?.toString()}",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 77.dp),
                        textAlign = TextAlign.Center, color = Color(0xff84B1B8)
                    )
                }

            }

            Spacer(modifier = Modifier.height(15.dp))
            PACard(
                iconCard = R.drawable.healt,
                txtCard = "Cartilla de vacunacion",
                colorIcon = Color(0xFF99F1A7)
            )

            Spacer(modifier = Modifier.height(25.dp))
            PACard(
                iconCard = R.drawable.proceedings,
                txtCard = "Expediente",
                colorIcon = Color(0xff78CEFF)
            )

            Spacer(modifier = Modifier.height(25.dp))
            PACard(
                iconCard = R.drawable.request,
                txtCard = "Solicitar constancia digital",
                colorIcon = Color(0xffF0E1FF)
            )
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}