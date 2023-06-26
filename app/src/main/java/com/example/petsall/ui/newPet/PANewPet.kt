package com.example.petsall.ui.newPet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.example.petsall.R
import com.example.petsall.presentation.newpets.PANewPetsEven
import com.example.petsall.presentation.newpets.PANewPetsViewModel
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.Black
import com.example.petsall.ui.theme.BtnBlue
import com.example.petsall.ui.theme.BtnGreen2
import com.example.petsall.ui.theme.plata
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PANewPet(navController: NavController, viewModel: PANewPetsViewModel = hiltViewModel()) {
    var state = viewModel.state

    val breedOptions = listOf(
        "Akita",
        "Alaskan Malamute",
        "Basset Hound",
        "Beagle",
        "Bernese Mountain Dog",
        "Bichón Frisé",
        "Bloodhound",
        "Boston Terrier",
        "Boxer",
        "Bulldog",
        "Bulldog Francés",
        "Bulldog Inglés",
        "Cane Corso",
        "Carlino",
        "Chihuahua",
        "Chin Japonés",
        "Chow Chow",
        "Cocker Spaniel",
        "Collie",
        "Corgi",
        "Dachshund",
        "Dálmata",
        "Doberman",
        "Dogo Argentino",
        "Elkhound Noruego",
        "Finnish Spitz",
        "Galgo Italiano",
        "Golden Retriever",
        "Gran Danés",
        "Husky Siberiano",
        "Jack Russell Terrier",
        "Keeshond",
        "Labrador Retriever",
        "Lhasa Apso",
        "Maltese",
        "Mastín",
        "Mastín Inglés",
        "Mastín Tibetano",
        "Old English Sheepdog",
        "Pastor Alemán",
        "Pastor Australiano",
        "Papillón",
        "Pekingese",
        "Pitbull",
        "Pomerania",
        "Poodle",
        "Rhodesian Ridgeback",
        "Rottweiler",
        "Saluki",
        "San Bernardo",
        "Setter Inglés",
        "Setter Irlandés",
        "Shiba Inu",
        "Shih Tzu",
        "Siberian Husky",
        "Terranova",
        "Vizsla",
        "Weimaraner",
        "Whippet",
        "Xoloitzcuintle",
        "Yorkipoo",
        "Yorkshire Terrier",
        "Zuchon"
    )
    val birthdate = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var selectedPet by rememberSaveable { mutableStateOf("dog") }
    val expandedState = rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    var selectedBreed by rememberSaveable { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            // Aquí puedes realizar las operaciones necesarias con la imagen seleccionada
            // por ejemplo, cargarla en una variable o guardarla en el almacenamiento
            if (uri != null) {
                // Manejar la imagen seleccionada aquí
                val imageBitmap = loadImageFromUri(uri, context.contentResolver)
                selectedImage = imageBitmap
                // Guardar la imagen en una variable
                Log.d("TAG", imageBitmap.toString())
                // ...
            }
        }
    )

    val color = Color(Black.value)
    val scrollState = rememberScrollState()
    val imageList = listOf(
        "dog" to R.drawable.dog_face,
        "fish" to R.drawable.fish,
        "cat" to R.drawable.cat_face,
        "bird" to R.drawable.bird
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

    Scaffold(topBar = {
        TopAppBar(

            title = {
                Text(
                    text = "Nuevo registro",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }, navigationIcon = {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 60.dp, vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona tu mascota",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 5.dp)
                ) {
                    imageList.take(2).forEach { (name, resourceId) ->
                        Spacer(modifier = Modifier.height(18.dp))
                        ImageWithSelection(painter = painterResource(id = resourceId),
                            contentDescription = name,
                            isSelected = selectedPet == name,
                            onClick = { selectedPet = name })
                    }
                }
                Spacer(modifier = Modifier.width(40.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 33.dp)
                ) {
                    imageList.takeLast(2).forEach { (name, resourceId) ->
                        Spacer(modifier = Modifier.height(18.dp))
                        ImageWithSelection(painter = painterResource(id = resourceId),
                            contentDescription = name,
                            isSelected = selectedPet == name,
                            onClick = { selectedPet = name })
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))


                OutlinedTextField(value = selectedBreed,
                    onValueChange = { selectedBreed = it },
                    label = { Text("Selecciona una raza") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable { expandedState.value = true }
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
                    readOnly = true
                )

                DropdownMenu(expanded = expandedState.value,
                    onDismissRequest = { expandedState.value = false }) {
                    breedOptions.forEach { breed ->
                        DropdownMenuItem(onClick = {
                            selectedBreed = breed
                            expandedState.value = false
                        }) {
                            Text(text = breed)
                        }
                    }
                }

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
                            Log.d("gvubhnjmk", birthdate.value)
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

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.None,
                                fontSize = 15.sp,
                                color = BtnBlue
                            )
                        ) {
                            append("Subir foto de perfil")
                        }
                    },
                    onClick = {
                        launcher.launch("image/*")
                    }, modifier = Modifier.padding(vertical = 20.dp)
                )

                ButtonDefault(
                    textButton = "Agregar", modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    if (name.isNotEmpty() && birthdate.value.isNotEmpty() && selectedBreed.isNotEmpty() && selectedPet.isNotEmpty()) {
                        viewModel.onEvent(
                            PANewPetsEven.Register(
                                name = name,
                                birthday = birthdate.value,
                                breeds = selectedBreed,
                                pets = selectedPet,
                                img = selectedImage
                            )
                        )
                        navController.navigate(Route.PAHome)
                    }
                }
            }
        }
    })
}

@Composable
fun ImageWithSelection(
    painter: Painter, contentDescription: String?, isSelected: Boolean, onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent
    val elevation = if (isSelected) 4.dp else 0.dp

    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp)),
        elevation = elevation,
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            Modifier.clip(RoundedCornerShape(12.dp))
        )
    }
    Text(
        text = if (isSelected) "Seleccionado" else "",
        color = borderColor,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )


}

@Composable
fun MyScreen() {
    val context = LocalContext.current
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            // Aquí puedes realizar las operaciones necesarias con la imagen seleccionada
            // por ejemplo, cargarla en una variable o guardarla en el almacenamiento
            if (uri != null) {
                // Manejar la imagen seleccionada aquí
                val imageBitmap = loadImageFromUri(uri, context.contentResolver)
                selectedImage = imageBitmap
                // Guardar la imagen en una variable
                Log.d("TAG", imageBitmap.toString())
                // ...
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        ClickableText(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.None,
                        fontSize = 15.sp,
                        color = BtnBlue
                    )
                ) {
                    append("Subir foto de perfil")
                }
            },
            onClick = {
                launcher.launch("image/*")
            }, modifier = Modifier.padding(vertical = 20.dp)
        )


    }

}

fun loadImageFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}