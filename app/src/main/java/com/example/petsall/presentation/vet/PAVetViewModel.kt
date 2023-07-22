package com.example.petsall.presentation.vet

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.vet.PAVetUseCase
import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class PAVetViewModel @Inject constructor(private val useCase: PAVetUseCase) : ViewModel() {

    var state by mutableStateOf(PAVetState())
        private set

    fun onEvent(event: PAVetEvent) {
        when (event) {
            is PAVetEvent.GetDataUser -> {
                viewModelScope.launch {
                    useCase.invoke().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                val locationValue = Location("location value.")
                                if (event.location != null) {
                                    val filterCoordinates = result.data.filter {
                                        locationValue.latitude = it?.data?.get("Latitud") as Double
                                        locationValue.longitude = it.data?.get("Longitud") as Double
                                        event.location.distanceTo(locationValue) < 10000
                                    }
                                    state = state.copy(data = filterCoordinates)
                                }else{
                                    state = state.copy(data = listOf())
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