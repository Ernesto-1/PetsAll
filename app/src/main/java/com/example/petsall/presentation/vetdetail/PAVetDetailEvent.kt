package com.example.petsall.presentation.vetdetail

import android.graphics.Bitmap

sealed class PAVetDetailEvent{
    data class GetCoordinates(val address: String) : PAVetDetailEvent()
    data class GetVet(val id: String) : PAVetDetailEvent()
    data class GetDataPets(val pet: String) : PAVetDetailEvent()
    data class RegisterDate(val day: String, val time: String,val patient: String,val reason: String,val idVet: String) : PAVetDetailEvent()




}
