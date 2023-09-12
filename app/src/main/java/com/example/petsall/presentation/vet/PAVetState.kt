package com.example.petsall.presentation.vet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.VetData

data class PAVetState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var dataVet: List<VetData> = listOf(),
    var dataVetFilter: List<VetData> = listOf(),
    var listSector: List<String> = listOf(),
    var listSpecialties: List<String> = listOf(),
    var dis: Int = 100,
    val selectedSector: MutableState<List<String>> = mutableStateOf(listOf()),
    val selectedSectorTemp: MutableState<List<String>> = mutableStateOf(listOf()),
    val selectedSpecialties: MutableState<List<String>> = mutableStateOf(listOf()),
    val selectedSpecialtiesTemp: MutableState<List<String>> = mutableStateOf(listOf())



)
