package com.example.petsall.ui.components.cards

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.petsall.ui.components.MyMap
import com.example.petsall.ui.components.MyMapWithoutMyLocation
import com.example.petsall.ui.theme.PAError
import com.example.petsall.ui.theme.PASucces
import com.example.petsall.ui.theme.stButton
import com.example.petsall.ui.theme.stMessages
import com.example.petsall.utils.checkLocationPermission
import com.example.petsall.utils.convertTimestampToString2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.maps.android.compose.*

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
                    when (data.status.toString()) {
                        STATUS_PROPOSAL, STATUS_CONFIRMED -> {
                            PAProposalStatus(
                                data = data,
                                patient = patient?.name ?: "",
                                isConfirmed = data.status.toString() == STATUS_CONFIRMED,
                                onClick = onClick
                            )
                        }
                        else -> {
                            message = when (data.status) {
                                STATUS_PENDING -> stringResource(id = R.string.home_pending_appointment)
                                else -> {
                                    ""
                                }
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
fun PAProposalStatus(
    data: PetDateMedic?,
    patient: String,
    isConfirmed: Boolean,
    onClick: (StatusDate) -> Unit = {}
) {

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
                    stringResource(id = if (isConfirmed) R.string.home_message_confirmed else R.string.home_message_proposal),
                    data.nameConsult,
                    patient,
                    datePet
                ),
                style = stMessages,
            )

            if (!isConfirmed) {
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
            } else {
                MyMapWithoutMyLocation(
                    modifier = Modifier.height(150.dp),
                    LatLng(data.lat, data.long),
                    nameBussines = data.nameConsult
                )
            }
        }
    }
}

sealed class StatusDate {
    object Accept : StatusDate()
    object Cancel : StatusDate()
}
