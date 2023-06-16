package com.example.petsall.ui.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeViewModel
import com.example.petsall.ui.navigation.Route

@Composable
fun PAHome(navController: NavController,activity: Activity,viewModel: PAHomeViewModel = hiltViewModel()) {
    val state = viewModel.state
    if (state.data?.isNotEmpty() == true) {
        val name by rememberSaveable { mutableStateOf(state.data?.get("Nombre")) }
        var selectPet by rememberSaveable { mutableStateOf("ThOd9bzBhhJDziG1YvBh") }
        LaunchedEffect(selectPet){
            viewModel.onEvent(PAHomeEvent.GetDataPets(selectPet))
        }

        BackHandler {
            activity.finish()
        }

        Column(
            modifier = Modifier
                .background(Color(0xffF9F9F9))
                .padding(vertical = 35.dp, horizontal = 40.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hola ${name}!",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))

            if (!state.dataPets.isNullOrEmpty()){
                Text(text = "Escoge a tu mascota para conocer su proxima cita", fontSize = 12.sp, modifier = Modifier.padding(horizontal = 40.dp), textAlign = TextAlign.Center)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = state.dataPets?.get(0)?.data?.get("Nombre").toString(), modifier = Modifier.padding(top = 20.dp) )
                }
            }
            Text(
                text = "No tienes peludos registrados",
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
                        append("Registrar a mi peludo")
                    }
                },
                onClick = {
                    navController.navigate(Route.PANewPet)
                }
            )

        }
    }
}