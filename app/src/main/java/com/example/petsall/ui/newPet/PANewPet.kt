package com.example.petsall.ui.newPet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.presentation.newpets.PANewPetsEven
import com.example.petsall.presentation.newpets.PANewPetsViewModel
import com.example.petsall.ui.login.ButtonDefault
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.Black
import com.example.petsall.ui.theme.plata

@Composable
fun PANewPet(navController: NavController,viewModel: PANewPetsViewModel = hiltViewModel()) {
    var state = viewModel.state

    val imageList = listOf(
        "dog" to R.drawable.dog_face,
        "fish" to R.drawable.fish,
        "cat" to R.drawable.cat_face,
        "bird" to R.drawable.bird

    )
    var selectedImage by remember { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var breeds by rememberSaveable { mutableStateOf("") }
    var birthdate by rememberSaveable { mutableStateOf("") }
    val color = Color(Black.value)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nuevo registro", fontSize = 24.sp)
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp)
            ) {
                imageList.take(2).forEach { (name, resourceId) ->
                    Spacer(modifier = Modifier.height(18.dp))
                    ImageWithSelection(
                        painter = painterResource(id = resourceId),
                        contentDescription = name,
                        isSelected = selectedImage == name,
                        onClick = { selectedImage = name }
                    )
                }
            }
            Spacer(modifier = Modifier.width(40.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 33.dp)
            ) {
                imageList.takeLast(2).forEach { (name, resourceId) ->
                    Spacer(modifier = Modifier.height(18.dp))
                    ImageWithSelection(
                        painter = painterResource(id = resourceId),
                        contentDescription = name,
                        isSelected = selectedImage == name,
                        onClick = { selectedImage = name }
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = breeds,
                onValueChange = { breeds = it },
                label = { Text("Raza") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = plata,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                    textColor = color
                ),
                textStyle = MaterialTheme.typography.body1
                )

            OutlinedTextField(
                value = birthdate,
                onValueChange = { birthdate = it },
                label = { Text("Fecha de nacimiento") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = plata,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                    textColor = color
                )
                )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(282.dp)
                    .padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = plata,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                    textColor = color
                ),
                textStyle = MaterialTheme.typography.body1,
                )

            ButtonDefault(textButton = "Agregar", modifier = Modifier.padding(horizontal = 40.dp)) {
                viewModel.onEvent(PANewPetsEven.Register(name = name, birthday = birthdate, breeds = breeds, pets = selectedImage))
                navController.navigate(Route.PAHome)
            }
        }
    }
    Log.d("thrthdrthrh", selectedImage)
}

@Composable
fun ImageWithSelection(
    painter: Painter,
    contentDescription: String?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xff84B1B8) else Color.Transparent
    val elevation = if (isSelected) 4.dp else 0.dp

    Surface(
        modifier = Modifier
            .border(1.dp, color = borderColor)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        elevation = elevation
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}