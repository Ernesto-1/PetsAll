package com.example.petsall.presentation.consultingroom.vetdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.data.remote.consultingroom.vetdetail.model.GeocodingResult

data class PAVetDetailState(
    val loadingRegister: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var successRegister: Boolean? = null,
    var data: List<GeocodingResult?> = listOf(),
    val selectedPet: MutableState<PetData> = mutableStateOf(PetData()),
    var dataVet: VetData? = VetData(),
    var dataPets: List<PetData> = listOf()


)
