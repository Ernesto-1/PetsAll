package com.example.petsall.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.home.PAHomeUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAHomeViewModel @Inject constructor(private val useCase: PAHomeUseCase) : ViewModel() {

    var state by mutableStateOf(PAHomeState())
        private set

    init {
        onEvent(PAHomeEvent.GetDataUser(""))
    }

    fun onEvent(event: PAHomeEvent) {
        when (event) {
            is PAHomeEvent.GetDataUser -> {
                viewModelScope.launch {
                    useCase.invoke().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(data = result.data?.data)
                            }
                            else -> {}
                        }
                    }
                }

            }
            is PAHomeEvent.GetDataPets ->{
                viewModelScope.launch {
                    useCase.getPets().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(numPets = result.data.size < 4 , dataPets = result.data )
                                state = if (event.pet?.isNotEmpty() == true){
                                    state.copy(dataPet = result.data.first() { it?.id == event.pet })
                                }else{
                                    state.copy(dataPet = result.data.first())
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAHomeEvent.GetDatePet ->{
                viewModelScope.launch {
                    useCase.getDatePet(event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(datePet = result.data)
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAHomeEvent.DeleteDatePet ->{
                viewModelScope.launch {
                    useCase.deleteDatePet(event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(isPetDelete = result.data)
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