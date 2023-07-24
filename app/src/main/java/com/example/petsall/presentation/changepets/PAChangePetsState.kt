package com.example.petsall.presentation.changepets

import com.google.firebase.firestore.DocumentSnapshot

data class PAChangePetsState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var dataPets: List<*>? = null,
    var dataPetsSearch: List<Any?>? = listOf()
)
