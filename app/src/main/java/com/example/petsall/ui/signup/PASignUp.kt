package com.example.petsall.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.presentation.login.PALoginEvent
import com.example.petsall.presentation.signup.PASignUpEvent
import com.example.petsall.presentation.signup.PASignUpViewModel
import com.example.petsall.ui.theme.BackGroud
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.ui.theme.txtGrey

@Composable
fun PASignUp(navController: NavController, viewModel: PASignUpViewModel = hiltViewModel()) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var hidden by rememberSaveable { mutableStateOf(true) }
    var hiddenConfirm by rememberSaveable { mutableStateOf(true) }
    val color = Color.Black


    LaunchedEffect(true) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize().padding(vertical = 25.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            modifier = Modifier.wrapContentSize().padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = color,
                    backgroundColor = Color.White,
                    focusedLabelColor = color.copy(alpha = ContentAlpha.high),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = color,
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),shape = RoundedCornerShape(16.dp),
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = color,
                    backgroundColor = Color.White,
                    focusedLabelColor = color.copy(alpha = ContentAlpha.high),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = color
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),shape = RoundedCornerShape(16.dp),
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electronico") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = color,
                    backgroundColor = Color.White,
                    focusedLabelColor = color.copy(alpha = ContentAlpha.high),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = color
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),shape = RoundedCornerShape(16.dp),
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {
                    if (password != "") {
                        IconButton(onClick = { hidden = !hidden }) {
                            val vector = painterResource(
                                if (hidden) com.example.petsall.R.drawable.ic_baseline_remove_red_eye_24 else com.example.petsall.R.drawable.ic_baseline_visibility_off_24
                            )
                            val description =
                                if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
                            Icon(painter = vector, contentDescription = description)
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = color,
                    backgroundColor = Color.White,
                    focusedLabelColor = color.copy(alpha = ContentAlpha.high),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = color
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),shape = RoundedCornerShape(16.dp),

            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if (hiddenConfirm) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {
                    if (confirmPassword != "") {
                        IconButton(onClick = { hiddenConfirm = !hiddenConfirm }) {
                            val vector = painterResource(
                                if (hiddenConfirm) com.example.petsall.R.drawable.ic_baseline_remove_red_eye_24 else com.example.petsall.R.drawable.ic_baseline_visibility_off_24
                            )
                            val description =
                                if (hiddenConfirm) "Ocultar contraseña" else "Revelar contraseña" //6
                            Icon(painter = vector, contentDescription = description)
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = color,
                    backgroundColor = Color.White,
                    focusedLabelColor = color.copy(alpha = ContentAlpha.high),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = color
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),shape = RoundedCornerShape(16.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (name.isNotEmpty() || lastName.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
                            if (password == confirmPassword) {
                                viewModel.onEvent(
                                    PASignUpEvent.Register(
                                        email = email,
                                        password = password,
                                        name = name,
                                        lastname = lastName
                                    )
                                )
                                if (viewModel.state.success) {
                                    navController.navigate(Route.PAHome)
                                }
                            }
                        }                    }
                )

            )

            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Text(
                    text = "Tienes que completar todos los campos",
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .width(282.dp),
                    color = BackGroud,
                    fontStyle = FontStyle.Italic
                )
                viewModel.state.message = ""
            } else {
                Text(
                    text = if (password != confirmPassword) "Las contraseñas no coinciden" else viewModel.state.message,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .width(282.dp),
                    color = BackGroud,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonDefault(
                textButton = "Registrarme",
                modifier = Modifier
                    .width(282.dp)
                    .padding(bottom = 25.dp)
                    .clip(RoundedCornerShape(12.dp)), radius = 12.dp
            ) {
                if (name.isNotEmpty() || lastName.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        viewModel.onEvent(
                            PASignUpEvent.Register(
                                email = email,
                                password = password,
                                name = name,
                                lastname = lastName
                            )
                        )
                        if (viewModel.state.success) {
                            navController.navigate(Route.PAHome)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrevSignUp(navController: NavController) {
    val listBenefits = listOf(
        "Lleva el control de tus mascotas en un solo lugar (cartilla, expediente y constancias).",
        "Agenda citas o comunicate con la veterinaria",
        "Encuentra al veterinario mas cercano a ti",
        "Conoce lugares, postres, alimento o accesorios para tu mascota cerca de ti"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Bienvenido a AllPets",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = GreenLight,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        LazyColumn {
            items(listBenefits) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ap_circle),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(10.dp),
                        tint = txtGrey
                    )
                    Text(text = it,color = txtGrey)
                }
            }
        }
        Column(modifier = Modifier.height(200.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Creemos en la excelencia profesional y sabemos que aquí en AllPets la encontraras", textAlign = TextAlign.Center, fontSize = 16.sp, color = txtGrey)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                ButtonDefault(textButton = "Siguiente", colorBackground = GreenLight, radius = 8.dp) {  navController.navigate(Route.PASignUp) }
            }
        }
    }
}