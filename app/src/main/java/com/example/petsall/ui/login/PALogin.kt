package com.example.petsall.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.login.PALoginEvent
import com.example.petsall.presentation.login.PALoginViewModel
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.*

@Composable
fun PALogin(navController: NavController,viewModel: PALoginViewModel = hiltViewModel()) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var hidden by rememberSaveable { mutableStateOf(true) } //1
    val color = Color(Black.value)

    LaunchedEffect(true) {

    }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.example.petsall.R.drawable.dise_o_sin_t_tulo),
                    contentDescription = "img_login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Fit
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = plata,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                            textColor = color
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ), shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            if (password != "") {
                                IconButton(onClick = { hidden = !hidden }) {
                                    val vector = painterResource(
                                        if (hidden) com.example.petsall.R.drawable.ic_baseline_remove_red_eye_24 else com.example.petsall.R.drawable.ic_baseline_visibility_off_24)
                                    val description =
                                        if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
                                    Icon(painter = vector, contentDescription = description)
                                }
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                        textStyle = MaterialTheme.typography.body1,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = plata,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                            textColor = color
                        )
                    )
                    if (viewModel.state.message.isNotEmpty()){
                        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())
                        Text(text = viewModel.state.message, textAlign = TextAlign.Center, color = BackGroud)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ButtonDefault(
                            textButton = "Iniciar sesion", modifier = Modifier.fillMaxWidth()
                        ) {
                            viewModel.onEvent(PALoginEvent.Login(email, password))
                            if (viewModel.state.success){
                                navController.navigate(Route.PAHome)
                            }
                        }
                        Text(
                            text = "Olvide mi contraseña",
                            color = Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .clickable {}
                                .padding(top = 5.dp))

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Registrarme",
                                color = Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .clickable { navController.navigate(Route.PASignUp) }
                                    .padding(bottom = 15.dp)
                            )
                        }
                    }

                }
            }
        }
}

@Composable
fun ButtonDefault(
    modifier: Modifier = Modifier,
    textButton: String? = "textBtn",
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BtnGreen2,
            contentColor = MaterialTheme.colors.surface
        ),modifier = modifier, shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = textButton ?: ""
        )
    }
}