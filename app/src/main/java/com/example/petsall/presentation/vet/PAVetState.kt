package com.example.petsall.presentation.vet

import com.google.firebase.firestore.DocumentSnapshot

data class PAVetState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<DocumentSnapshot?> = listOf()
)
