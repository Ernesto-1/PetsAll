package com.example.petsall.presentation.vet

import com.example.petsall.data.remote.model.VetData
import com.google.firebase.firestore.DocumentSnapshot

data class PAVetState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<VetData> = listOf(),
    var dis: Int = 100


)
