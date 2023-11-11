package com.example.petsall.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.petsall.ui.components.DismissBackground
import com.example.petsall.ui.components.PACard
import com.example.petsall.ui.components.PACard2
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.cards.PAStatusDate
import com.example.petsall.ui.components.cards.StatusDate
import com.example.petsall.ui.components.skeleton.TopBarSkeleton
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.stSubtitle
import com.example.petsall.ui.theme.stTitle
import com.example.petsall.utils.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
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
            state.selectPet.value = valor
        }

        viewModel.onEvent(PAHomeEvent.GetDataPets(state.selectPet.value))
        sheetState.hide()
    }

    LaunchedEffect(state.selectPet.value) {
        if (state.dataPets?.isNotEmpty() == true) {
            if (state.selectPet.value.isEmpty()) {
                viewModel.onEvent(PAHomeEvent.GetDataPets(""))
                sharedPreferences.edit().putString(
                    "idPet", state.selectPet.value
                ).apply()
            }
        }
    }

    state.dataUser?.name?.let {
        LaunchedEffect(key1 = state.dataUser?.name) {
            if (state.dataUser?.name?.isNotEmpty() == true) {
                sharedPreferences.edit().putString("nickName", state.dataUser!!.name).apply()
            }
        }
    }

    BackHandler {
        activity.moveTaskToBack(true)
    }
    LaunchedEffect(key1 = state.isPetDelete) {
        if (state.isPetDelete == true) {
            sheetState.hide()
            Toast.makeText(
                context, "Se elimino exitosamente de tu lista", Toast.LENGTH_LONG
            ).show()
            state.selectPet.value = ""
            state.isPetDelete = false
        }
    }

    LaunchedEffect(key1 = state.onChangeDate, key2 = state.datePet?.id) {
        if (state.onChangeDate) {
            viewModel.onEvent(PAHomeEvent.GetDataPets(state.datePet?.patient))
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                if (state.loadingPets) {
                    TopBarSkeleton()
                } else {
                    Column(verticalArrangement = Arrangement.Center) {
                        Row {
                            if (state.dataPets?.isNotEmpty() == true) {
                                AsyncImage(
                                    model = if (state.dataPet?.img?.isNotEmpty() == true) state.dataPet?.img else when (state.dataPet?.pet) {
                                        AppConstans.SpeciesConstants.FISH -> R.drawable.fish
                                        AppConstans.SpeciesConstants.DOG -> R.drawable.dog_face
                                        AppConstans.SpeciesConstants.CAT -> R.drawable.cat_face
                                        AppConstans.SpeciesConstants.BIRD -> R.drawable.bird
                                        else -> R.drawable.dog_face
                                    },
                                    contentDescription = "Img pet",
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(
                                        id = R.drawable.circle_24_image
                                    )
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = state.dataPet?.name.toString(),
                                    modifier = Modifier.align(
                                        Alignment.CenterVertically
                                    ),
                                    style = stSubtitle
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
                                        })
                            } else {
                                ButtonDefault(textButton = "Agregar mascota", radius = 12.dp) {
                                    navController.navigate(Route.PANewPet)
                                }
                            }
                        }
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
                            if (state.dataPets?.isNotEmpty() == true) {
                                LazyColumn {
                                    items(items = state.dataPets ?: mutableListOf(),
                                        key = { it.id.toString() }) { item ->
                                        var show by remember { mutableStateOf(true) }
                                        val dismissState =
                                            rememberDismissState(confirmStateChange = {
                                                if (it == DismissValue.DismissedToEnd) {
                                                    show = false
                                                    true
                                                } else false
                                            })
                                        AnimatedVisibility(
                                            show, exit = fadeOut(spring())
                                        ) {
                                            SwipeToDismiss(state = dismissState,
                                                modifier = Modifier,
                                                background = {
                                                    DismissBackground(dismissState)
                                                },
                                                dismissContent = {
                                                    CardChangePet(data = item,
                                                        item.id.toString(),
                                                        isSelected = state.selectPet.value == item.id.toString(),
                                                        onClick = {
                                                            if (state.selectPet.value != item.id.toString()) {
                                                                scope.launch {
                                                                    state.selectPet.value =
                                                                        item.id.toString()
                                                                    sharedPreferences.edit()
                                                                        .putString(
                                                                            "idPet",
                                                                            item.id.toString()
                                                                        ).apply()
                                                                    state.datePet =
                                                                        state.datePets?.firstOrNull { it.patient == item.id.toString() }
                                                                    state.dataPet =
                                                                        state.dataPets?.first { it.id == item.id.toString() }
                                                                    sheetState.hide()
                                                                }
                                                            }

                                                        })
                                                })
                                        }

                                        LaunchedEffect(show) {
                                            if (!show) {
                                                delay(800)
                                                viewModel.onEvent(PAHomeEvent.DeleteDatePet(item.id.toString()))
                                            }
                                        }

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
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.loadingPets) {
                    LoadingDialog {}
                }

                Text(
                    text = "Hola ${state.dataUser?.name}!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 35.dp),
                    style = stTitle
                )
                Spacer(modifier = Modifier.height(50.dp))

                PAStatusDate(
                    hasPets = state.dataPets?.isNotEmpty(),
                    data = state.datePet,
                    patient = state.dataPet
                ) {
                    when (it) {
                        StatusDate.Accept -> {
                            viewModel.onEvent(PAHomeEvent.UpdateStatusDate(state.datePet?.id ?: ""))
                        }
                        StatusDate.Cancel -> {
                            viewModel.onEvent(PAHomeEvent.DeleteDateQuote(state.datePet?.id ?: ""))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PACard2(
                        iconCard = R.drawable.healt,
                        txtCard = "Cartilla de vacunacion",
                        colorIcon = Color(0xFF99F1A7),
                        modifier = Modifier.weight(1f)
                    ) {
                        navController.navigate("${Route.PAVaccinationCard}/${user?.uid.toString()}/${state.dataPet?.id}")
                    }

                    PACard2(
                        iconCard = R.drawable.proceedings,
                        txtCard = "Expediente",
                        colorIcon = Color(0xff78CEFF),
                        modifier = Modifier.weight(1f)
                    ) {
                        navController.navigate("${Route.PAFiles}/${state.dataPet?.id}")
                    }
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

