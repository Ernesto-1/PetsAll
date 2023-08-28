package com.example.petsall.ui.business

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.petsall.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.presentation.business.PABusinessListViewModel
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.theme.BackGroud
import com.example.petsall.ui.theme.GreenLight
import com.example.petsall.ui.theme.Snacbar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PABusinessList(
    nameListBusiness: String,
    navController: NavController,
    viewModel: PABusinessListViewModel = hiltViewModel()
) {
    val state = viewModel.state
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
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            sheetContent = {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(unbounded = false)
                            .wrapContentHeight(unbounded = true)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HeaderBottomSheet()
                            if (state.dataBusinesSelected.value.name?.isNotEmpty() == true) {
                                Text(text = state.dataBusinesSelected.value.name ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = BackGroud)
                            }
                        }

                    }


            }) {
            Column(Modifier.fillMaxSize()) {
                if (state.dataBusiness.isNotEmpty()) {
                    LazyColumn {
                        items(state.dataBusiness) {
                            CardBusinessList(it) {
                                coroutine.launch {
                                    state.dataBusinesSelected.value  = it
                                    sheetState.show()
                                }

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
fun CardBusinessList(data: BusinessData = BusinessData(), onClick: () -> Unit = {}) {
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
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = if (data.imgBanner?.isNotEmpty() == true) data.imgBanner else R.drawable.alimento,
                contentDescription = "Img business",
                modifier = Modifier
                    .height(142.dp)
                    .fillMaxWidth(), contentScale = ContentScale.Crop
            )
            Text(
                text = data.name ?: "",
                modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                fontWeight = FontWeight.Bold,
                color = GreenLight,
                fontSize = 16.sp
            )
        }
    }
}