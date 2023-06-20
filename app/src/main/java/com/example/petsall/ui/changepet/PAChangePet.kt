package com.example.petsall.ui.changepet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.presentation.changepets.PAChangePetsViewModel
import com.example.petsall.ui.navigation.Route

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAChangePet(idPet: String = "",navController: NavController, viewModel: PAChangePetsViewModel = hiltViewModel()) {

    val state = viewModel.state
    var selectedImage by rememberSaveable { mutableStateOf(idPet) }
    //var warning by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(text = "Mis mascotas", color = Color.White, modifier = Modifier.fillMaxWidth().padding(end = 10.dp), textAlign = TextAlign.End)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                }, backgroundColor = Color(
                    0xff84B1B8
                )
            )
        }, content = {
            if (state.dataPets.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffF9F9F9)).padding(top = 45.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        items(state.dataPets) { item ->
                            CardChangePet(data = item?.data, "", isSelected = selectedImage == item?.id.toString()
                                ,onClick = { selectedImage =  item?.id.toString()})
                        }
                    }
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    textDecoration = TextDecoration.None,
                                    fontSize = 15.sp,
                                    color = Color(0xffDEC7F5)
                                )
                            ) {
                                append("Cambiar")
                            }
                        },
                        onClick = {
                            if (selectedImage.isNotEmpty()){
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "idPet",
                                    selectedImage
                                )
                                navController.navigateUp()
                            }
                        }, modifier = Modifier.padding(vertical = 50.dp)
                    )
                    /*if (selectedImage.isEmpty()){
                        //Tex
                    }*/
                }
            }
        }
    )






}

@Preview
@Composable
fun CardChangePet(data: Map<String, Any>? = mapOf(), id: String = "" ,isSelected: Boolean = false,onClick: () -> Unit = {}) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent
    if (data?.isNotEmpty() == true) {
        Column(modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(onClick = onClick)
                    .border(1.dp, borderColor, shape = RoundedCornerShape(16.dp)),
                elevation = if (isSelected) 4.dp else 0.dp, shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(16.dp)), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.fish),
                        contentDescription = "",
                        modifier = Modifier
                            .height(95.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(
                        modifier = Modifier
                            .border(0.5.dp, Color(0xff000000).copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    Text(text = data["Nombre"].toString(), fontSize = 15.sp)
                }
            }
            Text(text = if (isSelected) "Seleccionado" else "", color = borderColor, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

    }

}