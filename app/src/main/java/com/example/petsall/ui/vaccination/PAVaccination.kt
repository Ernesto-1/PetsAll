package com.example.petsall.ui.vaccination

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vaccination.PAVaccinationEvent
import com.example.petsall.presentation.vaccination.PAVaccinationViewModel
import com.example.petsall.ui.components.cards.CardVaccine
import com.example.petsall.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAVaccination(
    idUser: String,
    idPet: String,
    navController: NavController,
    viewModel: PAVaccinationViewModel = hiltViewModel()
) {
    val state = viewModel.state
    LaunchedEffect(Unit) {
        viewModel.onEvent(PAVaccinationEvent.GetVaccinationList(idUser = idUser, idPet = idPet))
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text ="Cartilla",
                color = Snacbar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                textAlign = TextAlign.End
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Snacbar
                )
            }
        }, backgroundColor = Color.White, elevation = 0.dp
        )
    }, content = {
        if (state.data.isNotEmpty()) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)) {
                items(state.data) {
                    CardVaccine(data = it)
                }

            }
        }
    })
}
