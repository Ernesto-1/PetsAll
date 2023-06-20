package com.example.petsall.presentation.changepets

import com.example.petsall.presentation.home.PAHomeEvent

sealed class PAChangePetsEvent  {
    data class GetDataPets(val pet: String) : PAChangePetsEvent()
}