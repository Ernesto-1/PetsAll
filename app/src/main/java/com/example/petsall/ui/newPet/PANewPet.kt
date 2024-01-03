package com.example.petsall.ui.newPet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.petsall.R
import com.example.petsall.presentation.newpets.PANewPetsEven
import com.example.petsall.presentation.newpets.PANewPetsViewModel
import com.example.petsall.ui.components.ImageWithSelection
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.BtnBlue
import com.example.petsall.ui.theme.Snacbar
import com.example.petsall.ui.theme.plata
import com.example.petsall.utils.convertStringToTimestamp
import com.example.petsall.utils.loadImageFromUri
import com.google.firebase.Timestamp
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ResourceType")
@Composable
fun PANewPet(navController: NavController, viewModel: PANewPetsViewModel = hiltViewModel()) {
    var selectPet =
        navController.currentBackStackEntryAsState().value?.savedStateHandle?.get<String>("selectPet")
            ?: ""
    var selectPetWithOutBreeds by rememberSaveable { mutableStateOf("") }
    var selectedItemGen by remember {
        mutableStateOf("Hembra")
    }
    val birthdate = rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val focusManager = LocalFocusManager.current
    var selectedPet by rememberSaveable { mutableStateOf("dog") }
    var name by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                val imageBitmap = loadImageFromUri(uri, context.contentResolver)
                selectedImage = imageBitmap
            }
        })

    val color = Color.Black
    val scrollState = rememberScrollState()
    val species = listOf(
        "dog" to R.drawable.icon_dog,
        "cat" to R.drawable.icon_cat,
        "other" to R.drawable.icon_all
    )
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            birthdate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            focusManager.clearFocus()
        }, mYear, mMonth, mDay
    )

    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            navController.navigate(Route.PAHome)
        }
    }

    Scaffold(topBar = {
        TopAppBar(

            title = {
                Text(
                    text = "Nuevo registro",
                    color = Snacbar,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Snacbar
                    )
                }
            }, backgroundColor = Color.White, elevation = 0.dp
        )
    }, content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 60.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona tu mascota",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            )
            Row(modifier = Modifier.padding(top = 20.dp)) {
                species.take(2).forEach { (nameSpecies, resourceId) ->
                    Spacer(modifier = Modifier.height(12.dp))
                    ImageWithSelection(painter = painterResource(id = resourceId),
                        contentDescription = nameSpecies,
                        isSelected = selectedPet == nameSpecies,
                        pet = selectedPet,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                        onClick = {
                            if (selectedPet != nameSpecies) {
                                selectedPet = nameSpecies
                                selectPet = ""
                            }
                        })
                }
            }
            Row {
                ImageWithSelection(painter = painterResource(id = R.drawable.icon_all),
                    contentDescription = "other",
                    isSelected = selectedPet == "other",
                    pet = selectedPet,
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f),
                    onClick = {
                        if (selectedPet != "other") {
                            selectedPet = "other"
                            selectPet = ""
                            selectPetWithOutBreeds = ""
                        }
                    })
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                TextField(value = if (selectedPet != "other") selectPet else selectPetWithOutBreeds,
                    onValueChange = {
                        if (selectedPet != "other") selectPet = it else selectPetWithOutBreeds =
                            it.filter { char -> char.isLetter() }
                    },
                    label = {
                        Text(
                            when (selectedPet) {
                                "dog", "cat" -> "Selecciona una raza"
                                "other" -> "¿Cual es tu mascota?"
                                else -> "Selecciona"
                            }
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable { //expandedState.value = true
                            if (selectedPet != "other") {
                                navController.navigate("${Route.PAChangePet}/${selectedPet}")
                            }
                        }
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
                        if (selectedPet != "other") {
                            IconButton(onClick = { navController.navigate("${Route.PAChangePet}/${selectedPet}") }) {
                                Icon(
                                    Icons.Filled.ArrowDropDown,
                                    contentDescription = "Expandir opciones"
                                )
                            }
                        }
                    },
                    readOnly = selectedPet != "other",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                )


                mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis
                OutlinedTextField(
                    value = birthdate.value,
                    onValueChange = { birthdate.value = it },
                    label = { Text("Fecha de nacimiento") },
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
                    readOnly = true
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
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
                )
                Spacer(modifier = Modifier.height(12.dp))
                selectedImage?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .clip(shape = RoundedCornerShape(50.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                APRadioButton(selectedItemGen) {
                    selectedItemGen = it
                }
                ClickableText(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.None,
                            fontSize = 15.sp,
                            color = BtnBlue,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        if (selectedImage == null) {
                            append("Foto de mi mascota")
                        } else {
                            append("Eliminar")
                        }

                    }
                }, onClick = {
                    if (selectedImage == null) {
                        launcher.launch("image/*")
                    } else {
                        selectedImage = null
                    }

                }, modifier = Modifier.padding(vertical = 20.dp)
                )
                if (state.loading) {
                    CircularProgressIndicator()
                }

                ButtonDefault(
                    textButton = "Agregar",
                    enabled = !state.loading,
                    modifier = Modifier.padding(horizontal = 40.dp),
                    radius = 16.dp
                ) {
                    if (name.isNotEmpty() && birthdate.value.isNotEmpty() && selectPet.isNotEmpty() || selectPetWithOutBreeds.isNotEmpty()) {
                        val timestamp = convertStringToTimestamp(birthdate.value)
                        viewModel.onEvent(
                            PANewPetsEven.Register(
                                dataNew = RegisterPets(
                                    name = name,
                                    gender = selectedItemGen,
                                    birthday = timestamp,
                                    breeds = if (selectedPet != "other") selectPet else "",
                                    pets = if (selectedPet != "other") selectedPet else selectPetWithOutBreeds,
                                    img = selectedImage
                                ),
                            )
                        )
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun APRadioButton(name: String = "Hembra", onItemSelected: (String) -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Genero")
        Row(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(text = "Hembra",fontSize = 12.sp)
                RadioButton(
                    selected = name == "Hembra",
                    onClick = { onItemSelected("Hembra") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BtnBlue,
                        unselectedColor = Color.Gray
                    )
                )
            }
            Column {
                Text(text = "Macho", fontSize = 12.sp)
                RadioButton(
                    selected = name == "Macho",
                    onClick = { onItemSelected("Macho") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BtnBlue,
                        unselectedColor = Color.Gray
                    )
                )
            }
        }
    }
}

data class RegisterPets(
    val name: String = "",
    val breeds: String = "",
    val gender: String = "",
    val birthday: Timestamp? = null,
    val pets: String = "",
    val img: Bitmap? = null
)