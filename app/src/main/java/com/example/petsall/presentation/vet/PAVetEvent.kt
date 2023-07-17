package com.example.petsall.presentation.vet

import android.location.Location

sealed class PAVetEvent{
    data class GetDataUser(val location: Location? = null) : PAVetEvent()
}
