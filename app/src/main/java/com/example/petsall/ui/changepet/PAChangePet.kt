package com.example.petsall.ui.changepet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petsall.R
import com.example.petsall.presentation.changepets.PAChangePetsEvent
import com.example.petsall.presentation.changepets.PAChangePetsViewModel
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.Black
import com.example.petsall.ui.theme.Check
import com.example.petsall.ui.theme.RoundedTxt
import com.example.petsall.ui.theme.plata
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAChangePet(
    petSelect: String = "",
    navController: NavController,
    viewModel: PAChangePetsViewModel = hiltViewModel()
) {

    val state = viewModel.state
    var selectedPet by rememberSaveable { mutableStateOf(petSelect) }
    var selectedBreed by rememberSaveable { mutableStateOf("") }
    val color = Color(Black.value)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onEvent(PAChangePetsEvent.GetDataPets(selectedPet))
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color(0xff84B1B8)
                    )
                }
                TextField(value = selectedBreed,
                    onValueChange = {
                        selectedBreed = it
                        viewModel.searchQuery(selectedBreed)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable { //expandedState.value = true

                        }
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = plata,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                        textColor = color,
                        focusedLabelColor = color
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                if (selectedBreed.isEmpty()) Icons.Filled.Search else Icons.Default.Close,
                                contentDescription = "Expandir opciones",
                                modifier = Modifier.clickable {
                                    if (selectedBreed.isNotEmpty()) {
                                        selectedBreed = ""
                                        viewModel.searchQuery("")
                                    }
                                }
                            )
                        }
                    }
                )
            }

        }, content = {
            if (state.dataPets != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffF9F9F9)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                        items(state.dataPetsSearch!!) {
                            Text(
                                text = it.toString(),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth().clickable {
                                    scope.launch {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "selectPet",
                                            it.toString()
                                        )
                                        navController.navigateUp()
                                    }
                                }, textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )

}

@Preview
@Composable
fun CardChangePet(
    data: Map<String, Any>? = mapOf(),
    id: String = "",
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    if (data?.isNotEmpty() == true) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (data["ImgUrl"].toString() != "") {
                    AsyncImage(
                        model = data["ImgUrl"].toString(),
                        contentDescription = "imageFromUrl",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(shape = RoundedCornerShape(50.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.fish),
                        contentDescription = "ImageLocal",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(shape = RoundedCornerShape(50.dp)),
                        contentScale = ContentScale.Fit
                    )

                }
                Text(
                    text = data["Nombre"].toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Medium
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "",
                    tint = Check,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 30.dp),
            backgroundColor = RoundedTxt

        ) {
        }
    }
}