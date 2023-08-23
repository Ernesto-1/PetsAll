package com.example.petsall.ui.business

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.petsall.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.theme.BtnGreen2
import com.example.petsall.ui.theme.Snacbar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PABusinessList(nameListBusiness: String,navController: NavController){
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = nameListBusiness,
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

        ModalBottomSheetLayout(sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.fillMaxSize().padding(0.dp),
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        HeaderBottomSheet()
                    }
                }
            }) {
            Column(Modifier.fillMaxSize()) {
                Log.d("nameList", nameListBusiness)
                LazyColumn{
                    item {
                        CardBusinessList(){
                            coroutine.launch {
                                sheetState.show()
                            }
                        }
                    }
                }
            }
        }
    }
    )

}


@Preview
@Composable
fun CardBusinessList(onClick: () -> Unit = {}){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(196.dp)
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(9.dp))
            .clickable {
                onClick.invoke()
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(9.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.alimento), contentDescription = "", modifier = Modifier
                .height(142.dp)
                .fillMaxWidth(), contentScale = ContentScale.Crop)
            Text(text = "Paletas Dogs", modifier = Modifier.padding(top = 12.dp, start = 12.dp), fontWeight = FontWeight.Bold, color = BtnGreen2, fontSize = 16.sp)
        }
    }
}