package com.example.petsall.ui.components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.Constants.STATUS_CONFIRMED
import com.example.petsall.Constants.STATUS_PENDING
import com.example.petsall.Constants.STATUS_PROPOSAL
import com.example.petsall.R
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.PetDateMedic
import com.example.petsall.ui.theme.PAError
import com.example.petsall.ui.theme.PASucces
import com.example.petsall.ui.theme.stButton
import com.example.petsall.ui.theme.stMessages
import com.example.petsall.utils.convertTimestampToString2
import com.google.firebase.Timestamp

@Composable
fun PAStatusDate(
    hasPets: Boolean?,
    data: PetDateMedic?,
    patient: PetData?,
    onClick: (StatusDate) -> Unit = {}
) {
    hasPets?.let {
        when (it) {
            true -> {
                var message = ""
                if (data == null) {
                    message = stringResource(id = R.string.home_no_appointment)
                } else {
                    if (data.status.toString() == STATUS_PROPOSAL) {
                        PAProposalStatus(
                            data = data,
                            patient = patient?.name ?: "",
                            onClick = onClick
                        )
                    } else {
                        message = when (data.status) {
                            STATUS_PENDING -> stringResource(id = R.string.home_pending_appointment)
                            STATUS_CONFIRMED -> {
                                String.format(
                                    stringResource(id = R.string.home_appointment),
                                    patient?.name,
                                    convertTimestampToString2(data.dateMedic as Timestamp)
                                )
                            }
                            else -> {
                                ""
                            }
                        }
                    }
                }

                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 77.dp),
                        textAlign = TextAlign.Center,
                        color = Color(0xff84B1B8)
                    )
                }
            }
            false -> {
                Text(
                    text = stringResource(id = R.string.home_no_register_pets),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 77.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xff84B1B8)
                )
            }
        }
    }
}

@Composable
fun PAProposalStatus(data: PetDateMedic?, patient: String, onClick: (StatusDate) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val datePet = convertTimestampToString2(data?.dateMedic as Timestamp)
            Text(
                text = String.format(
                    stringResource(id = R.string.home_message_proposal),
                    patient,
                    datePet
                ),
                style = stMessages,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    onClick = { onClick.invoke(StatusDate.Cancel) },
                    colors = ButtonDefaults.buttonColors(containerColor = PAError),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(text = "Cancelar", style = stButton)
                }
                Button(
                    onClick = { onClick.invoke(StatusDate.Accept) },
                    colors = ButtonDefaults.buttonColors(containerColor = PASucces),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(text = "Confirmar", style = stButton)
                }
            }
        }
    }
}

sealed class StatusDate {
    object Accept : StatusDate()
    object Cancel : StatusDate()
}
