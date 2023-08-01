package com.example.petsall.presentation.vaccination

sealed class PAVaccinationEvent{
    data class GetVaccinationList(val idUser: String, val idPet: String) : PAVaccinationEvent()

}