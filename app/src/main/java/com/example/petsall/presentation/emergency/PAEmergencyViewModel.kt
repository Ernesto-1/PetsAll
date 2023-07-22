package com.example.petsall.presentation.emergency

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.emergency.PAEmergencyUseCase
import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PAEmergencyViewModel @Inject constructor(private val useCase: PAEmergencyUseCase): ViewModel() {

    var state by mutableStateOf(PAEmergencyState())
        private set

    fun onEvent(event: PAEmergencyEvent) {
        when (event) {
            is PAEmergencyEvent.GetVetEmergency -> {
                viewModelScope.launch {
                    useCase.invoke().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                var distan = 100
                                val locationValue = Location("location value.")
                                if (event.location != null) {
                                    var locationVet : List<DocumentSnapshot?> = listOf()
                                    do {
                                        locationVet = result.data.filter {
                                            locationValue.latitude =
                                                it?.data?.get("Latitud") as Double
                                            locationValue.longitude =
                                                it.data?.get("Longitud") as Double
                                            event.location.distanceTo(locationValue) < distan
                                        }
                                        distan += 100
                                    }while (locationVet.isEmpty())

                                    state = state.copy(data = locationVet[0])
                                }else{
                                    state = state.copy(data = null)
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
