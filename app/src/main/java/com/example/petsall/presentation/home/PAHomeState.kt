package com.example.petsall.presentation.home

import com.google.firebase.firestore.DocumentSnapshot

data class PAHomeState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: Map<String, Any>? = mapOf(),
    var dataPet: DocumentSnapshot? = null,
    var numPets: Boolean = true,
    var dataPets: List<DocumentSnapshot?> = listOf(),
    var datePet: List<DocumentSnapshot?> = listOf(),
    var isPetDelete: Boolean? = false,


)
