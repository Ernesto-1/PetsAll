package com.example.petsall.presentation.vetdetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.changepets.PAChangePetsUseCase
import com.example.petsall.domain.vetdetail.PAVetDetailUseCase
import com.example.petsall.presentation.changepets.PAChangePetsEvent
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAVetDetailViewModel @Inject constructor(private val useCase: PAVetDetailUseCase,private val useCaseDataPets: PAChangePetsUseCase) : ViewModel() {

    var state by mutableStateOf(PAVetDetailState())
        private set

    fun onEvent(event: PAVetDetailEvent) {
        when (event) {
            is PAVetDetailEvent.GetCoordinates -> {
                viewModelScope.launch {
                    useCase.invoke(event.address).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(data = result.data.results)
                                Log.d("rcytvybuknl", state.data.toString())
                            }
                            else -> {}
                        }
                    }
                }

            }
            is PAVetDetailEvent.GetVet ->{
                viewModelScope.launch {
                    useCase.getVet(event.id).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(dataVet = result.data?.data)
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAVetDetailEvent.GetDataPets -> {
                viewModelScope.launch {
                    useCaseDataPets.getPets().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(dataPets = result.data)
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAVetDetailEvent.RegisterDate ->{
                viewModelScope.launch {
                    useCase.registerDate(day = event.day, time = event.time, patient = event.patient, reason = event.reason, idVet = event.idVet).collect() { result ->
                        when(result){
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Log.d("RegisterDate", result.data.toString())
                            }
                            is Resource.Failure -> {}
                        }
                    }
                }
            }
        }
    }

}