package com.example.petsall.ui.files

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.petsall.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petsall.presentation.files.PAFilesEvent
import com.example.petsall.presentation.files.PAFilesViewModel
import com.example.petsall.ui.components.bottom.HeaderBottomSheet
import com.example.petsall.ui.components.cards.RecordList
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
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var selectedItem by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
                            text = state.dataFileSelected.value.medicalMatter,
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
                                text = "Ced. Prof." + state.dataFileSelected.value.license,
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
                        state.dataFileSelected.value.treatment?.forEach {
                            OutlinedTextField(
                                value = it.toString(),
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
                        }

                        RecordList(data = state.dataFileSelected.value.medicalRecordList)

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
                        VetCard(
                            VetCardInfo(
                                date = convertTimestampToString(it?.date as Timestamp),
                                reason = it.medicalMatter,
                                vetLicense = "Ced.Prof. ${it.license}"
                            ),
                            isSelected = selectedItem == it.id
                        ) {
                            selectedItem = it.id
                            state.dataFileSelected.value = it

                            scope.launch {
                                sheetState.show()
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
fun VetCard(
    vetInfo: VetCardInfo = VetCardInfo(
        date = "2023-11-09",
        reason = "Consulta",
        vetLicense = "Ced.Prof.: 12345"
    ),
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable(onClick = onClick),
        elevation = 8.dp,
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) BtnBlue else Color.White
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Text(text = vetInfo.date, fontWeight = FontWeight.Bold)
                }

                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = vetInfo.reason, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_medical),
                    contentDescription = null,
                    tint = Color.Gray
                )
                Text(text = vetInfo.vetLicense, fontWeight = FontWeight.Bold)
            }
        }
    }
}

data class VetCardInfo(
    val date: String,
    val reason: String,
    val vetLicense: String
)
