package com.example.petsall.ui.files

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.data.remote.model.FilesDataClass
import com.example.petsall.presentation.files.PAFilesEvent
import com.example.petsall.presentation.files.PAFilesViewModel
import com.example.petsall.ui.components.ChipCard
import com.example.petsall.ui.components.GSPRMFlexLayout
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.listSpecialized
import com.example.petsall.ui.theme.*
import com.example.petsall.utils.convertTimestampToString
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PAFiles(
    idPet: String = "", navController: NavController, viewModel: PAFilesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    Log.d("hbinjmk", state.data.toString())

    LaunchedEffect(Unit) {
        viewModel.onEvent(PAFilesEvent.GetFiles(idPet = idPet))
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Expedientes",
                color = Snacbar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                textAlign = TextAlign.End
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Snacbar
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
                        .padding(horizontal = 16.dp)
                        .wrapContentWidth(unbounded = false)
                        .wrapContentHeight(unbounded = true)

                ) {
                    Column(
                        modifier = Modifier
                            .height(600.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        HeaderBottomSheet()
                        Text(
                            text = state.dataFileSelected.value.reason,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Purple200, modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (state.dataFileSelected.value.date != null) {
                                Text(
                                    text = ("Fecha: ${convertTimestampToString(state.dataFileSelected.value.date as Timestamp)}"),
                                    modifier = Modifier.padding(top = 4.dp),
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    fontSize = 12.sp,
                                    style = TextStyle.Default,
                                )
                            }
                            Text(
                                text = "Ced. Prof." + state.dataFileSelected.value.pLicense,
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = 12.sp,
                                style = TextStyle.Default
                            )
                        }
                        Text(
                            text = "Comentarios",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Purple200, modifier = Modifier.padding(vertical = 16.dp)
                        )
                        OutlinedTextField(
                            value = state.dataFileSelected.value.comments,
                            onValueChange = { comm ->

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            textStyle = MaterialTheme.typography.body1,
                            readOnly = true, enabled = false
                        )
                        Text(
                            text = "Diagnostico",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Purple200, modifier = Modifier.padding(vertical = 16.dp)
                        )
                        OutlinedTextField(
                            value = state.dataFileSelected.value.diagnosis,
                            onValueChange = { comm ->

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            textStyle = MaterialTheme.typography.body1,
                            readOnly = true, enabled = false
                        )

                        Text(
                            text = "Tratamiento",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Purple200, modifier = Modifier.padding(vertical = 16.dp)
                        )
                        OutlinedTextField(
                            value = state.dataFileSelected.value.treatment,
                            onValueChange = { comm ->

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            textStyle = MaterialTheme.typography.body1,
                            readOnly = true, enabled = false
                        )
                        listSpecialized(state.dataFileSelected.value.vaccines as List<*>?, title = "Vacunas")
                        listSpecialized(state.dataFileSelected.value.medicine as List<*>?, title = "Medicamentos")


                    }

                }
            }) {
            if (state.data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                ) {
                    items(state.data) {
                        CardFiles(data = it) {
                            if (it != null) {
                                coroutine.launch {
                                    state.dataFileSelected.value = it
                                    sheetState.show()

                                }

                            }
                        }
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun CardFiles(
    modifier: Modifier = Modifier,
    data: FilesDataClass? = FilesDataClass(),
    onClick: () -> Unit = {}
) {

    val date = convertTimestampToString(data?.date as Timestamp)
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .height(100.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                onClick.invoke()
            }),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = data.reason,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Purple200
                )
                Text(
                    text = ("Fecha: $date"),
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 12.sp,
                    style = TextStyle.Default
                )
                Text(
                    text = "Ced. Prof." + data.pLicense,
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = 12.sp,
                    style = TextStyle.Default
                )
            }
        }
    }
}