package com.example.petsall.presentation.vet

import android.location.Location

sealed class PAVetEvent{
    data class GetDataVet(val location: Location? = null) : PAVetEvent()

    data class FilterVets(val listSelectPet: List<String> = listOf(), val listSelectSpecialties: List<String> = listOf()) : PAVetEvent()

}
