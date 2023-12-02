package com.example.petsall.ui.forgotten

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.petsall.presentation.forgotten.PAForgottenPasswordEvent
import com.example.petsall.presentation.forgotten.PAForgottenPasswordViewModel
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.ui.theme.plata

@Composable
fun PAForgottenPassword(viewModel: PAForgottenPasswordViewModel = hiltViewModel()){
    val state = viewModel.state
    var email by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Ingresa tu correo electrónico",modifier = Modifier.padding(start = 20.dp,top = 30.dp, bottom = 8.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    if (newEmail.isEmpty() || newEmail.all { it.isLetterOrDigit() || it == '.' || it == '-' || it == '@' || it == '_'|| it == '&' || it == '$'|| it == '#'} ) {
                        email = newEmail
                    }
                },
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
                enabled = state.loading == null,
                textStyle = MaterialTheme.typography.body1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = plata,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                    textColor = Color.Black
                )
            )
            if(state.loading == true){
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 6.dp))
            }
            ButtonDefault(
                textButton = "Recuperar contraseña",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 10.dp),
                radius = 16.dp,
                colorBackground = GreenLight
            ) {
                if (email.matches(emailRegex)) {
                    viewModel.onEvent(PAForgottenPasswordEvent.ForgottenPassword(email))
                }
            }
            if (state.success == true){
                Text(text = "Enviamos un correo electrónico para restablecer la contraseña", modifier = Modifier.fillMaxWidth().padding(top = 8.dp), textAlign = TextAlign.Center)

            }
        }
    }
}