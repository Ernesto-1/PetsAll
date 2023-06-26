package com.example.petsall.ui.changepet

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petsall.R
import com.example.petsall.presentation.changepets.PAChangePetsViewModel
import com.example.petsall.presentation.newpets.PANewPetsEven
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.BtnGreen2

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
                    Text(text = "Mis mascotas", color = Color.White, modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp), textAlign = TextAlign.End)
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
                        .background(Color(0xffF9F9F9)), horizontalAlignment = Alignment.CenterHorizontally
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

                    ButtonDefault(
                        textButton = "Cambiar", modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        if (selectedImage.isNotEmpty()){
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "idPet",
                                selectedImage
                            )
                            navController.navigateUp()
                        }
                    }

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
                    if (data["ImgUrl"].toString() != ""){
                        AsyncImage(
                            model = data["ImgUrl"].toString(),
                            contentDescription = "imageFromUrl",
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.fish),
                            contentDescription = "ImageLocal",
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(text = data["Nombre"].toString(), fontSize = 15.sp, modifier = Modifier.padding(vertical = 6.dp), fontWeight = FontWeight.Medium)
                }
            }
            Text(text = if (isSelected) "Seleccionado" else "", color = borderColor, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

    }

}