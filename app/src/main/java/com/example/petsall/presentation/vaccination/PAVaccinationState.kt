package com.example.petsall.presentation.vaccination

import com.google.firebase.firestore.DocumentSnapshot

data class PAVaccinationState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<DocumentSnapshot?> = listOf()
)
