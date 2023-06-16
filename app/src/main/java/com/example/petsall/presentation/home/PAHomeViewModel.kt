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
        onEvent(PAHomeEvent.GetDataPets(""))

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
                                //if (result.data.documents.)
                                state = if (event.pet.isNotEmpty()){
                                    state.copy(dataPets = result.data?.documents?.filter { it.id == event.pet })
                                }else{
                                    state.copy(dataPets = result.data?.documents)
                                }
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