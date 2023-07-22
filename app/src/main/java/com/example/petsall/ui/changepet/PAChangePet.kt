package com.example.petsall.ui.changepet

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.petsall.presentation.changepets.PAChangePetsViewModel
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.theme.Check
import com.example.petsall.ui.theme.RoundedTxt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PAChangePet(
    idPet: String = "",
    navController: NavController,
    viewModel: PAChangePetsViewModel = hiltViewModel()
) {

    val state = viewModel.state
    var selectedImage by rememberSaveable { mutableStateOf(idPet) }
    //var warning by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Mis mascotas", color = Color.White, modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp), textAlign = TextAlign.End
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
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
                        .background(Color(0xffF9F9F9)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        items(state.dataPets) { item ->
                            CardChangePet(data = item?.data,
                                "",
                                isSelected = selectedImage == item?.id.toString(),
                                onClick = { selectedImage = item?.id.toString() })
                        }

                    }

                    ButtonDefault(
                        textButton = "Cambiar", modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        if (selectedImage.isNotEmpty()) {
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
                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier
                .weight(2f)
                .padding(vertical = 10.dp, horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
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
            if (isSelected){
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint = Check, modifier = Modifier.padding(end = 20.dp))
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp).padding(horizontal = 30.dp),
            backgroundColor = RoundedTxt

        ) {
        }
    }
}