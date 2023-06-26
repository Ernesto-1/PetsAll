package com.example.petsall.ui.vet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.vet.PAVetEvent
import com.example.petsall.presentation.vet.PAVetViewModel
import com.example.petsall.ui.navigation.Route

@Composable
fun PAVet(navController: NavController,viewModel:PAVetViewModel = hiltViewModel()) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.onEvent(PAVetEvent.GetDataUser(""))
    }

    if (state.data.isNotEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF9F9F9))) {
            LazyColumn(){
                items(state.data) {item ->
                    CardVet(data = item?.data, id = item?.id.toString()){
                        navController.navigate("${Route.PAVetDetail}/w")
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun CardVet(data:  Map<String, Any>? = mapOf(),id:String = "",onClick: () -> Unit = {}){
    if (data?.isNotEmpty() == true){
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .height(116.dp)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(16.dp)), elevation = 4.dp) {
            Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = data["Nombre"].toString(), fontSize = 17.sp, color = Color(0xff82649E))
                    Text(text = "4.9", fontSize = 10.sp)
                }
                Text(text = "Mascotas: Perros y gatos", fontSize = 12.sp)
                Text(text = id, fontSize = 12.sp)
            }

        }
    }

}