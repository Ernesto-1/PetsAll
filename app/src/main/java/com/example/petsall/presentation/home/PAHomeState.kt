package com.example.petsall.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.PetDateMedic
import com.example.petsall.data.remote.model.UserDataClass

data class PAHomeState(
    val loadingPets: Boolean = true,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var dataUser: UserDataClass? = UserDataClass(),
    var dataPet: PetData? = null,
    var numPets: Boolean = true,
    var dataPets: List<PetData>? = listOf(),
    var datePet: PetDateMedic? = null,
    var datePets: List<PetDateMedic>? = listOf(),
    var isPetDelete: Boolean? = false,
    var selectPet: MutableState<String> = mutableStateOf(""),
    var onChangeDate: Boolean = false
    )
