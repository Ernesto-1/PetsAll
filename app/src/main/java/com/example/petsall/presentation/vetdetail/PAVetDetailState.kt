package com.example.petsall.presentation.vetdetail

import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.data.remote.vetdetail.model.GeocodingResult

data class PAVetDetailState(
    val loadingRegister: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var successRegister: Boolean? = null,
    var data: List<GeocodingResult?> = listOf(),
    var dataVet: VetData? = VetData(),
    var dataPets: List<PetData> = listOf()


)
