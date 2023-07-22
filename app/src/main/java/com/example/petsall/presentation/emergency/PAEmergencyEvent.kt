package com.example.petsall.presentation.emergency

import android.location.Location

sealed class PAEmergencyEvent{
    data class GetVetEmergency(val location: Location? = null) : PAEmergencyEvent()
}
