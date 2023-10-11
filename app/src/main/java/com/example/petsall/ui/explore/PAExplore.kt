package com.example.petsall.ui.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petsall.R
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.theme.Snacbar
import com.example.petsall.ui.theme.White
import com.example.petsall.utils.encodeJson

data class CardItem(val id: Int, val imageRes: Int, val nameCard: String)

@Composable
fun PAExplore(navController: NavController) {
    val cardItems = listOf(
        CardItem(1, R.drawable.postres, "Postres"),
        CardItem(2, R.drawable.alimento, "Alimento"),
        CardItem(3, R.drawable.accesorios, "Accesorios")
    )
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
    }
    Column(Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Text(
                    text = "Encuentra cosas cerca de ti",
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Snacbar
                )
            }
            items(cardItems) { card ->
                CardExploreOptions(card = card){
                    if (!clicked) {
                        navController.navigate("${Route.PABusinessList}/${card.nameCard}")
                        clicked = !clicked
                    }
                }
            }
        }
    }
}

@Composable
fun CardExploreOptions(card: CardItem, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(106.dp)
            .border(1.dp, Color(0xffeaeaea), shape = RoundedCornerShape(9.dp))
            .clickable {
                onClick.invoke()

            },
        elevation = 4.dp,
        shape = RoundedCornerShape(9.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = card.imageRes),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 3.dp,
                        radiusY = 3.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                    ),
                contentScale = ContentScale.Crop
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 18.dp), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = card.nameCard,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}
