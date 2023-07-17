package com.example.petsall.presentation.vetdetail

import com.example.petsall.data.remote.vetdetail.model.GeocodingResult
import com.google.firebase.firestore.DocumentSnapshot

data class PAVetDetailState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<GeocodingResult?> = listOf(),
    var dataVet: Map<String, Any>? = mapOf(),
    var dataPets: List<DocumentSnapshot?> = listOf()


)
