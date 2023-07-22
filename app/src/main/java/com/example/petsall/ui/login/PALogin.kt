package com.example.petsall.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
    val state = viewModel.state
    val focusRequester = remember { FocusRequester() }
    val screenWidth = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenWidth * 0.55f)
    LaunchedEffect(state.success) {
        if (state.success){
            navController.navigate(Route.PAHome){
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
                    painter = painterResource(id = com.example.petsall.R.drawable.all_pets_logo),
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
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),shape = RoundedCornerShape(16.dp),
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
                            textColor = color
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
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
                                        if (hidden) com.example.petsall.R.drawable.ic_baseline_remove_red_eye_24 else com.example.petsall.R.drawable.ic_baseline_visibility_off_24)
                                    val description =
                                        if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
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
                            textButton = "Iniciar sesion", modifier = Modifier.wrapContentWidth()
                        ) {
                            viewModel.onEvent(PALoginEvent.Login(email, password))
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