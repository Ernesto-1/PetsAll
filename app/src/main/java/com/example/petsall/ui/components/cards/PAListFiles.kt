package com.example.petsall.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.R
import com.example.petsall.data.remote.model.Medicamento
import com.example.petsall.ui.theme.avGrayBorder
import com.example.petsall.ui.theme.avGrayShadow
import com.example.petsall.ui.theme.stText
import com.example.petsall.ui.theme.stTitle
import com.example.petsall.utils.AppConstans
import com.example.petsall.utils.capitalizeName

@Composable
fun RecordList(data: List<Medicamento>? = null) {
    data?.let { listVaccine ->
        if (listVaccine.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Cartilla",
                    style = stTitle.copy(fontSize = 14.sp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listVaccine.forEach { itemVaccine ->
                        RecordItem(
                            type = itemVaccine.nombre,
                            name = itemVaccine.nombre,
                            numMedicine = itemVaccine.numero_medicamento,
                            nextAplication = itemVaccine.nextAplication
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordItem(
    type: String,
    name: String,
    numMedicine: String,
    nextAplication: String
) {
    ContainerCard {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconRecord = if (type.lowercase() == AppConstans.TYPE_MEDICINE)
                R.drawable.ic_medicine else R.drawable.vaccine
            Image(
                painter = painterResource(
                    id = iconRecord
                ),
                contentDescription = "icon_vaccine",
                modifier = Modifier.width(60.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                ItemText(label = "Tipo: ", valueLabel = type)
                ItemText(label = "Tratamiento: ", valueLabel = name)
                ItemText(
                    modifier = Modifier,
                    label = "",
                    valueLabel = numMedicine,
                    align = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ContainerCard(content: @Composable () -> Unit = {}) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .toStyleCard(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

fun Modifier.toStyleCard(): Modifier {
    return this
        .shadow(
            elevation = 4.dp,
            spotColor = avGrayShadow,
            ambientColor = avGrayShadow
        )
        .border(
            width = 1.dp,
            color = avGrayBorder,
            shape = RoundedCornerShape(size = 10.dp)
        )
}

@Composable
fun ItemText(
    modifier: Modifier = Modifier,
    label: String,
    valueLabel: String,
    align: TextAlign = TextAlign.Left
) {
    if (valueLabel.isNotEmpty()) {
        androidx.compose.material3.Text(
            text = buildAnnotatedString {
                append(label)
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(valueLabel.capitalizeName())
                }
            },
            style = stText.copy(
                textAlign = align
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}