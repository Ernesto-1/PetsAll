package com.example.petsall.presentation.consultingroom.vet

import android.location.Location

sealed class PAVetEvent{
    data class GetDataVet(val location: Location) : PAVetEvent()

    data class FilterVets(val listSelectPet: List<String> = listOf(), val listSelectSpecialties: List<String> = listOf()) : PAVetEvent()

}
