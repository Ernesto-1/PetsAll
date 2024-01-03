package com.example.petsall.presentation.consultingroom.vetdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.consultingroom.vetdetail.PAVetDetailUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAVetDetailViewModel @Inject constructor(private val useCase: PAVetDetailUseCase) :
    ViewModel() {

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
                            }
                            else -> {}
                        }
                    }
                }

            }
            is PAVetDetailEvent.GetVet -> {
                viewModelScope.launch {
                    useCase.getVet(event.id).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(dataVet = result.data)
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAVetDetailEvent.GetDataPets -> {
                viewModelScope.launch {
                    useCase.getPets().collect() { result ->
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
            is PAVetDetailEvent.RegisterDate -> {
                viewModelScope.launch {
                    useCase.registerDate(event.data).collect() { result ->
                        when(result){
                            is Resource.Loading -> {
                                state = state.copy(loadingRegister = true)
                            }
                            is Resource.Success -> {

                                state = state.copy(
                                    successRegister = result.data,
                                    loadingRegister = false
                                )
                            }
                            is Resource.Failure -> {}
                        }
                    }
                }
            }
        }
    }
}