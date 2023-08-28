package com.example.petsall.presentation.home

sealed class PAHomeEvent{
    data class GetDataUser(val state: String) : PAHomeEvent()
    data class GetDataPets(val pet: String? = "") : PAHomeEvent()
    data class GetDatePet(val idPet: String = "",val userId: String = "") : PAHomeEvent()
    data class DeleteDatePet(val idPet: String = "") : PAHomeEvent()


}
