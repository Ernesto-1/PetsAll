package com.example.petsall.presentation.home

import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.PetDateMedic
import com.example.petsall.data.remote.model.UserDataClass
import com.google.firebase.firestore.DocumentSnapshot

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
    var isPetDelete: Boolean? = false,


    )
