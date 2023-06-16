package com.example.petsall.presentation.vet

sealed class PAVetEvent{
    data class GetDataUser(val state: String) : PAVetEvent()
}
