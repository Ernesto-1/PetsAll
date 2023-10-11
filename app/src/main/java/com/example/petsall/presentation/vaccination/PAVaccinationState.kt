package com.example.petsall.presentation.vaccination

import com.example.petsall.data.remote.model.VaccineDataClass

data class PAVaccinationState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<VaccineDataClass?> = listOf()
)
