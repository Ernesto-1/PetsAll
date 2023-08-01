package com.example.petsall.presentation.vaccination

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.vaccination.PAVaccinationUseCase
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeState
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAVaccinationViewModel @Inject constructor(private val useCase: PAVaccinationUseCase) : ViewModel() {

    var state by mutableStateOf(PAVaccinationState())
        private set


    fun onEvent(event: PAVaccinationEvent) {
        when (event) {
            is PAVaccinationEvent.GetVaccinationList -> {
                viewModelScope.launch {
                    useCase.invoke(idUser = event.idUser, idPet = event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(data = result.data)
                                Log.d("CartillaVacunacion", state.data.toString())
                            }
                            else -> {}
                        }
                    }
                }

            }
            else -> {}
        }
    }

}