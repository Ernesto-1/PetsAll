package com.example.petsall.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.PetDateMedic
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
                                state = state.copy(dataUser = result.data)
                            }
                            else -> {}
                        }
                    }
                }

            }
            is PAHomeEvent.GetDataPets -> {
                viewModelScope.launch {
                    useCase.getPets().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(loadingPets = true)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    numPets = (result.data?.size ?: 4) < 4,
                                    dataPets = result.data,
                                    loadingPets = false
                                )
                                if (event.pet?.isNotEmpty() == true) {
                                    state =
                                        state.copy(
                                            dataPet = result.data?.first() { it.id == event.pet },
                                            selectPet = mutableStateOf(result.data?.first() { it.id == event.pet }?.id.toString())
                                        )
                                    getAllDate(idPet = event.pet)
                                } else {
                                    state = state.copy(
                                        dataPet = result.data?.first(),
                                        selectPet = mutableStateOf(result.data?.first()?.id.toString())
                                    )
                                    getAllDate(idPet = "")
                                }
                            }
                            else -> {
                                state = state.copy(
                                    dataPet = null,
                                    numPets = true,
                                    dataPets = mutableListOf(),
                                    loadingPets = false
                                )

                            }
                        }
                    }
                }
            }
            is PAHomeEvent.DeleteDatePet -> {
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
            is PAHomeEvent.UpdateStatusDate -> {
                viewModelScope.launch {
                    useCase.updateStatusDate(event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(
                                    loadingPets = true
                                )
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    onChangeDate = result.data ?: false,
                                    datePet = state.datePet?.copy(id = ""),
                                    loadingPets = false
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAHomeEvent.DeleteDateQuote -> {
                viewModelScope.launch {
                    useCase.deleteDateQuote(event.idPet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(
                                    loadingPets = true
                                )
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    onChangeDate = result.data ?: false,
                                    datePet = state.datePet?.copy(id = ""),
                                    loadingPets = false
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
            else -> {}
        }
    }

    private fun getAllDate(idPet: String) {
        viewModelScope.launch {
            useCase.getDatePet().collect() { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {

                        state = state.copy(datePets = result.data as MutableList<PetDateMedic>?)

                        state = if (idPet.isNotEmpty()) {
                            state.copy(datePet = result.data?.firstOrNull { it.idPatient == idPet })

                        } else {
                            state.copy(datePet = result.data?.firstOrNull { it.idPatient == state.selectPet.value })

                        }

                    }
                    else -> {
                        state = state.copy(datePets = listOf())
                    }
                }
            }
        }
    }

}