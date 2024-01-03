package com.example.petsall.presentation.consultingroom.vetdetail

import com.example.petsall.presentation.model.RegisterDataRequest


sealed class PAVetDetailEvent {
    data class GetCoordinates(val address: String) : PAVetDetailEvent()
    data class GetVet(val id: String) : PAVetDetailEvent()
    data class GetDataPets(val pet: String) : PAVetDetailEvent()
    data class RegisterDate(val data: RegisterDataRequest) : PAVetDetailEvent()





}