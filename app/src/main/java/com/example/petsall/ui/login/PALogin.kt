package com.example.petsall.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.presentation.login.PALoginEvent
import com.example.petsall.presentation.login.PALoginViewModel
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.BackGroud
import com.example.petsall.ui.theme.BtnBlue
import com.example.petsall.ui.theme.RedAlert
import com.example.petsall.ui.theme.plata

@Composable
fun PALogin(navController: NavController, viewModel: PALoginViewModel = hiltViewModel()) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var hidden by rememberSaveable { mutableStateOf(true) }
    var errorEmail by rememberSaveable { mutableStateOf(false) }
    var errorPassword by rememberSaveable { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    val state = viewModel.state
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    val focusRequester = remember { FocusRequester() }
    val screenWidth = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenWidth * 0.55f)
    LaunchedEffect(state.success) {
        if (state.success) {
            navController.navigate(Route.PAHome) {
                launchSingleTop = true
                popUpTo(Route.PALogin) {
                    inclusive = true
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_svg_sinbackground),
                contentDescription = "img_login",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { newEmail ->
                        if (newEmail.isEmpty() || newEmail.all { it.isLetterOrDigit() || it == '.' || it == '-' || it == '@' || it == '_'|| it == '&' || it == '$'|| it == '#'} ) {
                            email = newEmail
                            errorEmail = false
                            state.message = ""
                        }
                    },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ), shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequester.requestFocus()
                        }
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = plata,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                        textColor = Color.Black
                    ), isError = errorEmail || state.message.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { pass ->
                        password = pass
                        errorPassword = false
                        state.message = ""
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        if (password != "") {
                            IconButton(onClick = { hidden = !hidden }) {
                                val vector = painterResource(
                                    if (hidden) com.example.petsall.R.drawable.ic_baseline_remove_red_eye_24 else com.example.petsall.R.drawable.ic_baseline_visibility_off_24
                                )
                                val description =
                                    if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                                Icon(painter = vector, contentDescription = description)
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onEvent(PALoginEvent.Login(email, password))
                        }
                    ),
                    singleLine = true,
                    visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = plata,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                        textColor = Color.Black
                    ), isError = errorPassword || state.message.isNotEmpty()
                )

                if (errorEmail || state.message.isNotEmpty() || errorPassword) {
                    Text(
                        text = state.message.ifEmpty { "Campo obligatorio" },
                        color = RedAlert,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonDefault(
                        textButton = "Iniciar sesion",
                        modifier = Modifier.wrapContentWidth(),
                        radius = 16.dp
                    ) {
                        if (email.isNotEmpty() && password.isNotEmpty() && email.matches(emailRegex)) {
                            loading = true
                            viewModel.onEvent(PALoginEvent.Login(email, password))
                        } else {
                            errorEmail = true
                            errorPassword = true
                            if (!email.matches(emailRegex)) {
                                state.message = "Nombre de usuario o contraseña no validos"
                            }
                        }

                    }
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Route.PAForgotten)
                            }
                            .padding(top = 5.dp))

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = "¿No tienes cuenta? Registrate",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clickable { navController.navigate(Route.PrevSignUp) }
                                .padding(bottom = 16.dp)
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
    radius: Dp = 0.dp,
    colorBackground: Color = BtnBlue,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enabled) colorBackground else BackGroud,
            contentColor = MaterialTheme.colors.surface
        ), modifier = modifier, shape = RoundedCornerShape(radius)
    ) {
        Text(
            text = textButton ?: ""
        )
    }
}