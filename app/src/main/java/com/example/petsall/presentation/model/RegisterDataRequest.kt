package com.example.petsall.presentation.model

import com.google.firebase.Timestamp

data class RegisterDataRequest(
    val requestedAppointment: Timestamp? = null,
    val idConsult: String? = "",
    val patient: String? = "",
    val reason: String? = "",
    val lat: Double? = 0.0,
    val long: Double? = 0.0,
    val nameConsult: String? = "",
    val pet: String? = "",
    val age: String? = "",
    val idPatient: String? = ""
)