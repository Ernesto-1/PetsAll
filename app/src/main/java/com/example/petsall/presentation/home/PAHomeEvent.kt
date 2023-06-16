package com.example.petsall.presentation.home

sealed class PAHomeEvent{
    data class GetDataUser(val state: String) : PAHomeEvent()
    data class GetDataPets(val pet: String) : PAHomeEvent()

}
