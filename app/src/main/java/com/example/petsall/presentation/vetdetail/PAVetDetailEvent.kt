package com.example.petsall.presentation.vetdetail

import com.google.firebase.Timestamp


sealed class PAVetDetailEvent{
    data class GetCoordinates(val address: String) : PAVetDetailEvent()
    data class GetVet(val id: String) : PAVetDetailEvent()
    data class GetDataPets(val pet: String) : PAVetDetailEvent()
    data class RegisterDate(val day: Timestamp?, val time: String, val patient: String, val reason: String, val idVet: String) : PAVetDetailEvent()




}
